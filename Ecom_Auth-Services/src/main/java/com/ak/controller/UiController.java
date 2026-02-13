package com.ak.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.ak.entity.Cart;
import com.ak.entity.User;
import com.ak.repository.UserRepository;
import com.ak.service.ICartService;
import com.ak.service.INotificationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class UiController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired																
    private ICartService cartService;
    
    @Autowired
    private INotificationService notificationService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    // Home / Dashboard - redirects based on user role
    //Admin -> Admin Dashboard 
    //user -> Proudct catalog
    
    public String home(Principal principal) {
    	
    	if(principal == null) {
    		return "redirect:/login";
    	}
    	
    	User user =userRepository.findByEmail(principal.getName())
    			.orElse(null);
    	
    	if(user!=null && "Admin".equals(user.getRole())) {
    		return "redirect:/admin";
    	}
    	
    	return "redirect:/products";
    }
    
    
    
    //products page  - main catalog for users
    
    //show all products  with add to cart functionality
    
    public String productsPage(Model model, Principal principal) {
    	
    	 try {
    		 
    		 //Fetch Products from product serivce
    		 HttpHeaders headers=new HttpHeaders();
    		 headers.set("X-Internal-Calls", "AUTH-SERVICE");
    		 HttpEntity<String> entity=new HttpEntity<>(headers);
    		 
    		 //accept raw response
    		 ResponseEntity<String> rawResponse=restTemplate.exchange("http://localhost:8086/products",HttpMethod.GET,entity,String.class);
    		 
    		 //parse the data
    		 ObjectMapper mapper= new ObjectMapper();
    		 List<Map<String, Object>> products=mapper.readValue(rawResponse.getBody(), new TypeReference<List<Map<String,Object>>>(){});
    		 
    		 //get user detials
    		 User user =userRepository.findByEmail(principal.getName())
    				 .orElseThrow(()->new RuntimeException("user not found"));
    		 
    		 //get cartItem count
    		Cart cart=cartService.getCartByUserId(user.getId());
    		
    		int cartItemCount=cart.getItems().size();
    		
    		//Get unread notification count
    		Long unreadNotifications=notificationService.getUnreadCount(user.getId());
    		
    		model.addAttribute("unreadNotifications",unreadNotifications);
    		model.addAttribute("username",principal.getName());
    		model.addAttribute("products",products);
    		model.addAttribute("cartItemCount", cartItemCount);
    		model.addAttribute("user", user);
    		
    		 
    		 
    	 }
    	 catch(Exception e) {
    		 System.out.println("Error loading products" + e.getMessage());
    		 model.addAttribute("products", new ArrayList<>());
    	 }
    	 
    	 return "products";
    }
    
    
    

	/*
	 * @GetMapping("/products") public String productsPage(Model model, Principal
	 * principal) {
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.set("X-Internal-Calls",
	 * "AUTH_SERVICE"); HttpEntity<String> entity = new HttpEntity<>(headers);
	 * 
	 * try { ResponseEntity<String> rawResponse = restTemplate.exchange(
	 * "http://localhost:8086/products", HttpMethod.GET, entity, String.class );
	 * 
	 * System.out.println(" Raw response: " + rawResponse.getBody());
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); List<Map<String, Object>> products
	 * = mapper.readValue( rawResponse.getBody(), new TypeReference<List<Map<String,
	 * Object>>>() {} );
	 * 
	 * System.out.println(" Parsed size: " + products.size());
	 * model.addAttribute("products", products);
	 * 
	 * } catch (Exception e) { System.out.println(" Error: " + e.getMessage());
	 * model.addAttribute("products", new ArrayList<>()); }
	 * 
	 * model.addAttribute("username", principal.getName()); return "products"; }
	 */
    
    
    

    @GetMapping("/orders")
    public String ordersPage(Model model, Principal principal) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Calls", "AUTH_SERVICE");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8086/orders",
                HttpMethod.GET,
                entity,
                String.class
            );
            
            ObjectMapper mapper=new ObjectMapper();
            List<Map<String,Object>> orders=mapper.readValue(response.getBody(),new TypeReference<List<Map<String,Object>>> () {});
            
            
            
            
            //Get user details
            User user=userRepository.findByEmail(principal.getName())
            		.orElseThrow(()->new RuntimeException("user not found"));
            
            //Filters order by current user
            List<Map<String,Object>> userOrders=orders.stream()
            		.filter(order -> {
            			
            			Object userIdObj=order.get("userId");
            			
            			if(userIdObj instanceof Integer) {
            				return ( (Integer) userIdObj ).longValue() == user.getId();
            			}
            			
            			return userIdObj!=null && userIdObj.equals(user.getId());
            		}).toList();
            
            
            model.addAttribute("orders", userOrders);
            model.addAttribute("username", principal.getName());
          
        } catch (Exception e) {
            System.out.println(" Order service not available: " + e.getMessage());
            model.addAttribute("orders", new ArrayList<>());
        }

        model.addAttribute("username", principal.getName());
        return "orders";
    }
}