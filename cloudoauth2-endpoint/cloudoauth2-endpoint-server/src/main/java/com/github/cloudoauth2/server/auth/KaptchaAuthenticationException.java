package com.github.cloudoauth2.server.auth;

import org.springframework.security.core.AuthenticationException;

public class KaptchaAuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = 1L;
	
	public KaptchaAuthenticationException() {
		super("验证码错误");
	}
	
	public KaptchaAuthenticationException(String msg) {
		super(msg);
	}
}
