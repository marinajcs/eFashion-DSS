package com.dss.spring.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootUriController {
	@GetMapping("/")
    public String index() {
      return "login";
    }
	
	@GetMapping("/login")
    public String login() {
      return "login";
    }
	
}
