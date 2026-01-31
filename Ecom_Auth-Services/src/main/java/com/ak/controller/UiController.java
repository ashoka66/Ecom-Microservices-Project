package com.ak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.ws.rs.core.Response;

@Controller
public class UiController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/products")
    public String productsPage(Model model) {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.set("X-Internal-Calls", "AUTH_SERVICE");
    	HttpEntity<String> entity = new HttpEntity<>(headers);
    	System.out.println("Making request API Gateway with headers " + headers);
    	
    	try {
    		
    		ResponseEntity<List> response = restTemplate.exchange("http://localhost:8086/products",
    				                                              HttpMethod.GET,entity,
    				                                               List.class);
    		
    		System.out.println("Response from API Gateway : " + response.getStatusCode());
    		model.addAttribute("Products",response.getBody());
    		return "products";
    	}
    	catch(Exception e){
    		System.out.println("Error calling api gateway " + e.getMessage());
    		throw e;
    		
    	}

         
    }

    @GetMapping("/orders")
    public String ordersPage(Model model) {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.set("X-Internal-Calls", "AUTH_SERVICE");
    	HttpEntity<String> entity =new HttpEntity<>(headers);
    	
    	ResponseEntity<List> response = restTemplate.exchange("http://localhost:8086/orders",HttpMethod.GET,entity,List.class);

        
        model.addAttribute("orders", response.getBody());
        return "orders";
    }
}
