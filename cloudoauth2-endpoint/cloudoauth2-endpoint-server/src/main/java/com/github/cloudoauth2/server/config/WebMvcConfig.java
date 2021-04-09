package com.github.cloudoauth2.server.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.google.code.kaptcha.servlet.KaptchaServlet;

@Configuration
public class WebMvcConfig {
	
	@Bean
	public ErrorPageRegistrar errorPageRegistrar(){
		return new MyErrorPageRegistrar();
	}
	
	private static class MyErrorPageRegistrar implements ErrorPageRegistrar {

		@Override
		public void registerErrorPages(ErrorPageRegistry registry) {
			registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
			
			registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/5xx"));
			registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/5xx"));
			registry.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401"));
			registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
		}
	}
	
	@Bean
	public ServletRegistrationBean<KaptchaServlet> kaptchaServlet() {
	    return new ServletRegistrationBean<KaptchaServlet>(new KaptchaServlet(), "/kaptcha");
	}
}
