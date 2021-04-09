package com.github.cloudoauth2.server.config;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(AuthServerConfig.class);
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ApprovalStore approvalStore;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Bean
	@Autowired
	public ClientDetailsService clientDetails(DataSource dataSource) {
		JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		return clientDetailsService;
	}

	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(this.dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore);
		endpoints.approvalStore(this.approvalStore);
		endpoints.authenticationManager(this.authenticationManager);
		endpoints.reuseRefreshTokens(true);
		endpoints.accessTokenConverter(this.jwtAccessTokenConverter);
		endpoints.pathMapping("/oauth/confirm_access","/zfoauth/confirm_access");
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.realm("oauth2/client");
		security.allowFormAuthenticationForClients();
		security.tokenKeyAccess("permitAll()");
		security.checkTokenAccess("permitAll()");
	}
}
