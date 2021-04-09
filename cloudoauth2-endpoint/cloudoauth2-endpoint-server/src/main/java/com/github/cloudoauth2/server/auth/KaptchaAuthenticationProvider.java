package com.github.cloudoauth2.server.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.code.kaptcha.Constants;

public class KaptchaAuthenticationProvider implements AuthenticationProvider{
	
	@Value("${cloud.authentication.login.enableKaptcha:false}")
	private boolean enable = false;
	
	private AuthenticationProvider delegate;
	
	public KaptchaAuthenticationProvider(AuthenticationProvider delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(enable) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String kaptchInRequest = request.getParameter("kaptcha");
			if(StringUtils.isEmpty(kaptchInRequest)) {
				throw new KaptchaAuthenticationException("验证码不能为空");
			}
			String trimKaptchInRequest = kaptchInRequest.trim();
			if(StringUtils.isEmpty(trimKaptchInRequest)) {
				throw new KaptchaAuthenticationException("验证码不能为空");
			}
			String kaptchInSession = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if(kaptchInRequest.equals(kaptchInSession)) {
				return this.delegate.authenticate(authentication);
			}
			throw new KaptchaAuthenticationException("验证码不正确");
		}else {
			return this.delegate.authenticate(authentication);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return this.delegate.supports(authentication);
	}
}
