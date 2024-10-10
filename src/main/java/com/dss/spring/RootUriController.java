package com.dss.spring;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootUriController {
	@GetMapping("/")
    public String index() {
      return "index";
    }
	
	@GetMapping("/admin")
    public String admin() {
      return "admin";
    }
	/*
	@GetMapping("/products")
    public String products() {
      return "products";
    }
    */
}
