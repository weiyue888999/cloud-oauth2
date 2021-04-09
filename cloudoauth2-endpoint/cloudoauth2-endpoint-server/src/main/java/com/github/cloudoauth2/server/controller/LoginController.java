/**
 * <p>Copyright (R) 2014 正方软件股份有限公司。<p>
 */
package com.github.cloudoauth2.server.controller;

import javax.servlet.http.HttpServletRequest;

import com.github.cloudoauth2.server.auth.KaptchaAuthenticationException;
import com.github.cloudoauth2.server.auth.LoginFormAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@Value("${cloud.authentication.login.enableKaptcha:false}")
	private boolean enableKaptcha = false;

	@RequestMapping(path="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request,@RequestParam(required=false,defaultValue="false")boolean error) {
		
		CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if(token != null) {
			request.setAttribute("token", token);
		}
		AuthenticationException exception = (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(exception != null) {
			if(exception instanceof BadCredentialsException) {
				request.setAttribute("error", "用户名或密码错误");
			}else if(exception instanceof LoginFormAuthenticationException) {
				request.setAttribute("error", exception.getMessage());
			}else if(exception instanceof KaptchaAuthenticationException) {
				request.setAttribute("error", exception.getMessage());
			}else {
				request.setAttribute("error", "未知错误");
			}
			request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		request.setAttribute("enableKaptcha", enableKaptcha);
		
		return "login";
	}
}
