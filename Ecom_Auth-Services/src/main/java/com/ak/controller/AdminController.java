package com.ak.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		model.addAttribute("products",products);
		model.addAttribute("orders", orders);
		model.addAttribute("username", principal.getName());
		
		
		return "admin-dashboard";
		
		}
		catch(Exception e) {
			System.out.println("Admin dashboard error" + e.getMessage());
			model.addAttribute("error", "Failed to load dashboard data");
			return "admin-dashboard";
		}
		
	}
	
	
	//show add product form
	@GetMapping("/products/add")
	public String showAddProductForm(Model model, Principal principal) {
		
		model.addAttribute("username",principal.getName());
		return "admin-add-product";
	}
	
	//handle add product submission
	@PostMapping("/products/add")
	public String addProduct(@RequestParam String name, 
			                 @RequestParam String description,
			                 @RequestParam Double price,
			                 @RequestParam Integer stock,
			                 @RequestParam String category,
			                 @RequestParam String imageUrl) {
		
		try {
			HttpHeaders headers= new HttpHeaders();
			headers.set("X-Internal-Calls", "AUTH_SERVICE");
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			//create product json
			String productJson=String.format("{\"name\":\"%s\",\"description\":\"%s\",\"price\":%s,\"stock\":%s,\"category\":\"%s\",\"imageUrl\":\"%s\"}",
					 name, description, price, stock, category, imageUrl);
			
			
			HttpEntity<String> entity = new HttpEntity<>(productJson,headers);
			
			restTemplate.exchange("http://localhost:8086/products", HttpMethod.POST,entity,String.class);
			
			return "redirect:/admin?success=product_added";
		}
		catch(Exception e) {
			System.out.println("Error adding product: " + e.getMessage());
			
			return "redirect:/admin/products/add?error=falied";
		}
		
	}
	
	
	//delete product
	
	@PostMapping("/products/{id}/delete")
	public String deleteProduct(@PathVariable Long id) {
		
		try {
			
			HttpHeaders headers= new HttpHeaders();
			headers.set("X-Internal-Calls", "AUTH_SERVICE");
			HttpEntity<String> entity=new HttpEntity<>(headers);
			
			restTemplate.exchange("http://localhost:8086/products/" + id, HttpMethod.DELETE,entity,String.class);
			
			return "redirect:/admin?success=proudct_deleted";
			
		}
		catch(Exception e) {
			System.out.println("Error deleting product" + e.getMessage());
			return "redirect:/admin?error=delete_failed";
			
		}
	}
	

}
