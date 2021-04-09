package com.github.cloudoauth2.common;

public enum OauthUserStatus {

	ENABLED("1"),DISABLED("0");
	
	private String value;
	
	private OauthUserStatus(String val){
		this.value = val;
	}
	
	public String getValue() {
		return this.value;
	}
}
