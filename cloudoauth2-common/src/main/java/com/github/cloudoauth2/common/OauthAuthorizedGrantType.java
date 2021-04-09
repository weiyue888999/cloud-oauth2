package com.github.cloudoauth2.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OauthAuthorizedGrantType {
	
	public static final String AUTHORIZATION_CODE = "authorization_code";
	
	public static final String IMPLICIT = "implicit";
	
	public static final String PASSWORD = "password";
	
	public static final String CLIENT_CREDENTIALS = "client_credentials";
	
	public static final List<String> GRANT_TYPES = Collections.unmodifiableList(Arrays.asList(AUTHORIZATION_CODE,IMPLICIT,PASSWORD,CLIENT_CREDENTIALS));
}
