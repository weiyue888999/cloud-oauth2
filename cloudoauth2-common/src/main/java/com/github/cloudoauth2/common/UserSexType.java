package com.github.cloudoauth2.common;

public enum UserSexType {

	UNKNOW("0"),MAN("1"),WOMAN("2");
	
	private String value;
	
	private UserSexType(String val){
		this.value = val;
	}
	
	public String getValue() {
		return this.value;
	}
}
