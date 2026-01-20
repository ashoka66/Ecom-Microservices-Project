package com.ak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	  
	  @GetMapping
	  public String productsPage(Model model) {
		   
		  List<?> products= restTemplate.getForObject("http://localhost:8086/products", List.class);
		  model.addAttribute("products",products);
		  return "products";
		  
	  }
	  
	  
	  public String OrdersPage(Model model) {
		  List<?> orders=restTemplate.getForObject("http://localhost:8087/orders", List.class);
		  model.addAttribute("orders",orders);
		  return "orders";
	  }


}
