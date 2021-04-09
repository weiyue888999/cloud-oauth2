package com.github.cloudoauth2.server.config;

import javax.sql.DataSource;

import com.github.cloudoauth2.server.auth.KaptchaAuthenticationProvider;
import com.github.cloudoauth2.server.auth.LoginFormAuthenticationProvider;
import com.github.cloudoauth2.server.password.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		// jdbcUserDetailsManager.setEnableAuthorities(false);
		// jdbcUserDetailsManager.setEnableGroups(false);
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		
		//正方密码处理机制
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
		authenticationProvider.setUserDetailsService(jdbcUserDetailsManager());
		
		//谁放在最外面，谁先验证
		
		//验证码验证
		AuthenticationProvider kaptchAuthenticationProvider = new KaptchaAuthenticationProvider(authenticationProvider);
		
		//用户名或密码输入验证
		AuthenticationProvider loginFormAuthenticationProvider = new LoginFormAuthenticationProvider(kaptchAuthenticationProvider);
		
		return loginFormAuthenticationProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		//数据库用户验证
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//安全方面
		http.headers().httpStrictTransportSecurity().disable();
		
		http.authorizeRequests()
		.antMatchers("/static/**/*.*").permitAll()
		.antMatchers("/kaptcha").permitAll()
		.anyRequest().authenticated().and().formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/index")
		.failureUrl("/login?error=true")
		.permitAll();
	}
}
