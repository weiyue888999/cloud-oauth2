package com.github.cloudoauth2.server.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cloudoauth2.server.service.OauthScopeService;

@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationController {
	
	private static final Map<String,String> USER_ATTR_NAME_MAP = new HashMap<String,String>();
	
	static {
		USER_ATTR_NAME_MAP.put("user_name", "用户名");
		USER_ATTR_NAME_MAP.put("type", "用户类型");
		USER_ATTR_NAME_MAP.put("mobile_number", "手机号码");
		USER_ATTR_NAME_MAP.put("email", "电子邮箱");
		USER_ATTR_NAME_MAP.put("sex", "用户性别");
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private OauthScopeService oauthScopeService;
	
	@RequestMapping("/zfoauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request,Principal principal) throws Exception {
		ModelAndView view = new ModelAndView();
		
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
		
		//client图标
		{
			String clientId = authorizationRequest.getClientId();
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
			byte[] iconByte = jdbcTemplate.queryForObject("select icon_byte from zfcloud_oauth_client_details where client_id = ? ", new String[] {clientId}, byte[].class);
			String type = jdbcTemplate.queryForObject("select icon_type from zfcloud_oauth_client_details where client_id = ? ", new String[] {clientId}, String.class);
			if(iconByte != null) {
				byte[] encodeResult = Base64.encodeBase64(iconByte);
				String iconBase64Str = new String(encodeResult);
				request.setAttribute("clientImgSrc", "data:image/"+type+";base64,"+iconBase64Str);
			}
		}
		//登录人图标
		{
			String userName = principal.getName();
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
			byte[] iconByte = jdbcTemplate.queryForObject("select icon_byte from zfcloud_oauth_users where user_name = ? ", new String[] {userName}, byte[].class);
			String type = jdbcTemplate.queryForObject("select icon_type from zfcloud_oauth_users where user_name = ? ", new String[] {userName}, String.class);
			if(iconByte != null) {
				byte[] encodeResult = Base64.encodeBase64(iconByte);
				String iconBase64Str = new String(encodeResult);
				request.setAttribute("userImgSrc", "data:image/"+type+";base64,"+iconBase64Str);
			}
		}
		
		Set<String> scopes = authorizationRequest.getScope();
		Set<String> exposeAttrSet = this.oauthScopeService.getAttrsByScopes(scopes);
		
		List<String> exposeAttrNameList = new ArrayList<String>();
		
		for(String attr : exposeAttrSet) {
			String name = USER_ATTR_NAME_MAP.get(attr);
			exposeAttrNameList.add(name);
		}
		view.addObject("exposeAttrNameList", exposeAttrNameList);
		
		CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if(token != null) {
			view.addObject("token",token);
		}
		view.addObject("authorizationRequest", authorizationRequest);
		view.setViewName("authorization");
		return view;
	}
}