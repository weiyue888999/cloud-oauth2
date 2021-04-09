package com.github.cloudoauth2.server.common;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static String getRemoteAddress(HttpServletRequest request) {
		String address = request.getHeader("X-Real-IP");
		if(address == null) {
			address = request.getRemoteAddr();
		}
		return address;
	}
	
	public static String getRemoteProxyAddress(HttpServletRequest request) {
		return request.getHeader("X-Real-IP");
	}
}
