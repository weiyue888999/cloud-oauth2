package com.github.cloudoauth2.common;

public enum OauthUserSourceType {

	UNKNOW("0"),SYSTEM_INPUT("1");
	
	private String value;
	
	private OauthUserSourceType(String val){
		this.value = val;
	}
	
	public String getValue() {
		return this.value;
	}
}
