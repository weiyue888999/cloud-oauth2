/**
 * <p>Copyright (R) 2014 正方软件股份有限公司。<p>
 */
package com.github.cloudoauth2.server.config;

import com.github.cloudoauth2.server.token.JwtAccessTokenScopeConverter;
import com.github.cloudoauth2.server.token.TokenConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class OauthTokenConfig {
	
	private static final Logger log = LoggerFactory.getLogger(OauthTokenConfig.class);
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keys/key-oauth.jks"),"123123.".toCharArray());
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenScopeConverter();
		jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("key-oauth2"));
		jwtAccessTokenConverter.setAccessTokenConverter(new TokenConverter());
		return jwtAccessTokenConverter;
	}

	@Bean
	@Qualifier("tokenStore")
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
}
