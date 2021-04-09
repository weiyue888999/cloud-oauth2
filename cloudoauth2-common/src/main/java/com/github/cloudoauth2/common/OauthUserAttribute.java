package com.github.cloudoauth2.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OauthUserAttribute {

	public static final String USER_NAME = "user_name";
	
	public static final String TYPE = "type";
	
	public static final String MOBILE_NUMBER = "mobile_number";
	
	public static final String EMAIL = "email" ;
	
	public static final String SEX = "sex";
	
	public static final List<String> MUST_INPUT_ATTRS = Collections.unmodifiableList(Arrays.asList(USER_NAME));
	
	public static final List<String> ATTRS = Collections.unmodifiableList(Arrays.asList(USER_NAME,TYPE,MOBILE_NUMBER,EMAIL,SEX));
	
}
