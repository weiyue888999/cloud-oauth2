package com.github.cloudoauth2.server.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OauthScopeService {

	@Autowired
	private DataSource dataSource;

	private String query_attrs_sql = "select attrs from ZFCLOUD_OAUTH_SCOPE where scope = ?";
	
	public Set<String> getAttrsByScopes(Set<String> scopes){
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);

		// 获得要暴露的属性列表
		Set<String> exposeAttrSet = new HashSet<String>();
		for (String scope : scopes) {
			String exposeAttrs = jdbcTemplate.queryForObject(query_attrs_sql, new Object[] { scope }, String.class);
			if (exposeAttrs != null && !exposeAttrs.equals("")) {
				String[] exposeAttrArrays = StringUtils.split(exposeAttrs, ',');
				if (exposeAttrArrays != null && exposeAttrArrays.length > 0) {
					exposeAttrSet.addAll(Arrays.asList(exposeAttrArrays));
				}
			}
		}
		return exposeAttrSet;
	}
}
