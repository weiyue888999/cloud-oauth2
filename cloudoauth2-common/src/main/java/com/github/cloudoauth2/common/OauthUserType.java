package com.github.cloudoauth2.common;

public enum OauthUserType {
	
	UNKNOW("0"),TEACHER("1"),STUDENT("2"),TS("3");
	
	private String value;
	
	private OauthUserType(String val){
		this.value = val;
	}
	
	public String getValue() {
		return this.value;
	}
}
