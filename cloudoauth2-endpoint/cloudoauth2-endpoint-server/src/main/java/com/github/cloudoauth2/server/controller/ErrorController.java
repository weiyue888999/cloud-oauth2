package com.github.cloudoauth2.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	@RequestMapping("/401")
	public String error401(HttpServletRequest request) {
		return "error/5xx";
	}

	@RequestMapping("/404")
	public String error404(HttpServletRequest request) {
		return "error/404";
	}
	
	@RequestMapping("/5xx")
	public String error5xx(HttpServletRequest request) {
		return "error/5xx";
	}
}
