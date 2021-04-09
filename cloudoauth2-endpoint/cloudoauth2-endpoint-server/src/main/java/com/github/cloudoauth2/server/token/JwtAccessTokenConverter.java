/**
 * <p>Copyright (R) 2014 正方软件股份有限公司。<p>
 */
package com.github.cloudoauth2.server.token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**public**/ class JwtAccessTokenConverter extends org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter {

	private static final Logger log = LoggerFactory.getLogger(JwtAccessTokenConverter.class);
	
	@Autowired
	private DataSource dataSource;
	
	private String query_attrs_sql = "select expose_attrs from oauth_client_details where client_id = ?";
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		return super.enhance(accessToken, authentication);
	}

	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		
		Map<String,Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);
		
		Set<String> scopes = token.getScope();
		
		String clientId = authentication.getOAuth2Request().getClientId();
		String username = (String) authentication.getPrincipal();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		
		//获得要暴露的属性列表
		String exposeAttrs = jdbcTemplate.queryForObject(query_attrs_sql, new Object[] {clientId}, String.class);
		String[] exposeAttrArrays = StringUtils.split(exposeAttrs, ',');
		if(exposeAttrArrays == null || exposeAttrArrays.length <= 0) {
			return map;
		}
		
		List<String> exposeAttrsTableColumnName = Arrays.asList(exposeAttrArrays);
		String columnNames = StringUtils.join(exposeAttrsTableColumnName,",");
		
		//取得全面的用户信息
		String sql = "select " + columnNames + " from oauth_users where user_name = ? "; 
		Map<String,Object> userAttrMap = jdbcTemplate.queryForMap(sql, new Object[] {username});
		
		Map<String,Object> userAttrMapKeyLowerCase =  new HashMap<String,Object>();
		Iterator<Entry<String, Object>> it = userAttrMap.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Object> e = it.next();
			String key = e.getKey();
			Object value = e.getValue();
			
			String keyLowerCase = key.toLowerCase();
			userAttrMapKeyLowerCase.put(keyLowerCase, value);
		}
		
		//user_name属性需要作为必填暴露属性
		userAttrMapKeyLowerCase.remove("user_name");
		
		map.putAll(userAttrMapKeyLowerCase);
		return map;
	}
}
