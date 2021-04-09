package com.github.cloudoauth2.server.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class LoginFormAuthenticationProvider implements AuthenticationProvider{

	private AuthenticationProvider delegate;
	
	public LoginFormAuthenticationProvider(AuthenticationProvider delegate) {
		super();
		this.delegate = delegate;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Object principal = authentication.getPrincipal();
		if(principal == null) {
			throw new LoginFormAuthenticationException("用户名不能为空");
		}else {
			if(principal instanceof String) {
				String principalStr = (String)principal;
				String trimPrincipalStr = principalStr = principalStr.trim();
				if(trimPrincipalStr.equals("")) {
					throw new LoginFormAuthenticationException("用户名不能为空");
				}
			}
		}
		
		Object credentials = authentication.getCredentials();
		if(credentials == null) {
			throw new LoginFormAuthenticationException("密码不能为空");
		}else {
			if(credentials instanceof String) {
				String credentialsStr = (String)credentials;
				String trimCredentialsStr = credentialsStr = credentialsStr.trim();
				if(trimCredentialsStr.equals("")) {
					throw new LoginFormAuthenticationException("密码不能为空");
				}
			}
		}
		return this.delegate.authenticate(authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return this.delegate.supports(authentication);
	}

}
