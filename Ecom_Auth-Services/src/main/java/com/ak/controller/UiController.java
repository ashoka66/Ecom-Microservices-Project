package com.ak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class UiController {
	
	 @Autowired
	  private RestTemplate restTemplate;

	  @GetMapping("/login")
	  public String loginPage() {
	    return "login";
	  }


}
