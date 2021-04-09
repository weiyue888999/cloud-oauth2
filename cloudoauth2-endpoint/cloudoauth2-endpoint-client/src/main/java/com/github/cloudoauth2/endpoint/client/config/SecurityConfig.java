package com.github.cloudoauth2.endpoint.client.config;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

//@Configuration
public class SecurityConfig implements WebSecurityConfigurer<WebSecurity>{
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true);
	}

	@Override
	public void init(WebSecurity builder) throws Exception {
	}
}