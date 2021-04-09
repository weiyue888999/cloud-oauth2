package com.github.cloudoauth2.server.auth;

import org.springframework.security.core.AuthenticationException;

public class LoginFormAuthenticationException extends AuthenticationException{
	
	private static final long serialVersionUID = 1L;
	
	public LoginFormAuthenticationException(String msg) {
		super(msg);
	}
}
