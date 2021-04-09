package com.github.cloudoauth2.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@RequestMapping("/")
	public String home() {
		return "/";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index page";
	}
	
	@RequestMapping("/demo")
	public String demo() {
		throw new IllegalStateException("违法的参数");
	}
}
