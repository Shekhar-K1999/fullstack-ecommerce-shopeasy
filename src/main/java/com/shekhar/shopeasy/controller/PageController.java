package com.shekhar.shopeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/login")
	public String loginPage() {
		return "/auth/login";
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "/auth/register";
	}
	
}
