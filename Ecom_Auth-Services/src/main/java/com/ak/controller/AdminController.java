package com.ak.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	//admin dashboard - overview of products and orders
	
	
	public String adminDashboard(Model model, Principal principal) {
		
		
		try {
		HttpHeaders headers=new HttpHeaders();
		headers.set("X-Internal-Calls", "AUTH_SERVICE");
		HttpEntity<String> entity=new HttpEntity<>(headers);
		
		ResponseEntity<String> productsResponse =restTemplate.exchange("http://localhost:8086/products",HttpMethod.GET,
				                                                entity,String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		List<Map<String,Object>> products=mapper.readValue(productsResponse.getBody(), new TypeReference<List<Map<String,Object>>>(){});
		
		
		//fetch orders
		ResponseEntity<String> ordersResponse =restTemplate.exchange( "http://localhost:8086/orders",HttpMethod.GET, entity,String.class);
		
		List<Map<String,Object>> orders=mapper.readValue(ordersResponse.getBody(),new TypeReference<List<Map<String,Object>>>() {});
		
		 
		
		}
		
	}

}
