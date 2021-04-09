package com.github.cloudoauth2.endpoint.client.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@RequestMapping("/home")
	@ResponseBody
	public String home() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			String name = authentication.getName();
			return "home page,loginName:"+name;			
		}else {
			return "honme page no login";
		}
	}
	
	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return "name:"+name;
	}
}
