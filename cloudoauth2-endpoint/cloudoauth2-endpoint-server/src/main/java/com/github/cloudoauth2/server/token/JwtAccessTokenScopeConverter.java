package com.github.cloudoauth2.server.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.github.cloudoauth2.server.service.OauthScopeService;

public class JwtAccessTokenScopeConverter extends org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter {

	private static final Logger log = LoggerFactory.getLogger(JwtAccessTokenConverter.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private OauthScopeService oauthScopeService;

	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

		Map<String, Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);

		Set<String> scopes = token.getScope();
		// 获得要暴露的属性列表
		Set<String> exposeAttrSet = this.oauthScopeService.getAttrsByScopes(scopes);
		
		if(exposeAttrSet.isEmpty()) {
			return map;
		}

		List<String> exposeAttrsTableColumnName = new ArrayList<String>(exposeAttrSet);
		String columnNames = StringUtils.join(exposeAttrsTableColumnName, ",");

		// 取得全面的用户信息
		String sql = "select " + columnNames + " from oauth_users where user_name = ? ";
		
		String username = (String) authentication.getPrincipal();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		
		Map<String, Object> userAttrMap = jdbcTemplate.queryForMap(sql, new Object[] { username });

		Iterator<Entry<String, Object>> it = userAttrMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			String key = e.getKey();
			Object value = e.getValue();

			String keyLowerCase = key.toLowerCase();
			
			map.putIfAbsent(keyLowerCase, value);
		}
		map.remove("aud");
		return map;
	}
}
