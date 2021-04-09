package com.github.cloudoauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication(scanBasePackages="com.github.cloudoauth2")
@EnableAuthorizationServer
@EnableResourceServer
@EnableWebSecurity
@EnableDiscoveryClient
public class CloudOauth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudOauth2ServerApplication.class, args);
	}
}

