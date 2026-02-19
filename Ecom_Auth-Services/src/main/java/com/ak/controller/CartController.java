package com.ak.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ak.entity.Cart;
import com.ak.entity.CartItem;
import com.ak.entity.User;
import com.ak.repository.UserRepository;
import com.ak.service.ICartService;
import com.ak.service.INotificationService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private INotificationService notificationService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Disply cart page
	
	@GetMapping
	public String viewCart(Model model , Principal principal) {
		
		//get userid from authenticated user 
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()-> new RuntimeException("user not found"));
		
		//Get users' cart
		Cart cart=cartService.getCartByUserId(user.getId());
		
		model.addAttribute("cart", cart);
		model.addAttribute("username", principal.getName());
		
		return "cart";
		
	}
	
	//add product to cart(ajax endpoint)
	
	@PostMapping("/add")
	@ResponseBody
	public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue="1") Integer quantity, Principal principal) {
		
		try {
			
			User user=userRepository.findByEmail(principal.getName())
					.orElseThrow(()-> new RuntimeException("user not found"));
			cartService.addUser(user.getId(), productId, quantity);
			
			 System.out.println(" Product added to cart, redirecting...");
			  return "redirect:/products?success=added";
		}
		catch(Exception e) { 
			System.out.println("Error adding to cart " + e.getMessage());
			return "redirect:/products?error=failed";
		}
		
	}
	
	
	
	//update cartItem quantity
	@PostMapping("/update")
    public String removeItem(@RequestParam Long cartItemId, @RequestParam Integer quantity, Principal principal) {
    	
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()-> new RuntimeException("user not found"));
		
		cartService.updateCartItemQuantity(user.getId(), cartItemId, quantity);
		
		return "redirect:/cart";
			
	 }  

	
	//Remove item from cart
	@DeleteMapping("/delete/{cartItemId}/")
	public String removeItem(@RequestParam Long cartItemId, Principal principal) {
		
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()->new RuntimeException("user not found"));
		
		cartService.removeFromCart(user.getId(), cartItemId);
		return "redirect:/cart";
                		
	}
	
	
	
	//simple place order - convert cart items to orders 
	@PostMapping("/place-order")
	public String placeOrderFromCart(Principal principal) {
		
		try {
		
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()->new RuntimeException("user not found"));
		
		Cart cart=cartService.getCartByUserId(user.getId());
		
		if(cart.getItems().isEmpty()) {
			return "redirect:/cart?error:empty";
		}
		
		//create order for each cart item
		for(CartItem item : cart.getItems()) {
			
			createOrder(user.getId(),item.getProductId(),item.getQuantity());
		}
		
		//clear cart after successfull order
		cartService.clearCart(user.getId());
		
		//create notification
	
		
		notificationService.createNotification(user.getId(), 
				                  "Order placed successfully !  Total: Rs" + 
		                        cart.getTotalPrice(), "ORDER_PLACED");
		
		
		return "redirect:/cart?success=true";
		
		}
		catch(Exception e) {
			System.out.println("place order error " + e.getMessage());
			e.printStackTrace();
			return "redirect:/cart?error=failed";
		}
		
		
	}
	
	
	//Helper method
	private void createOrder(Long userId, Long productId, Integer quantity) {
		
		try {
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
//			 headers.set("X-Internal-Calls", "AUTH_SERVICE");
			 
			 String orderJson=String.format(
			            "{\"userId\":%d,\"productId\":%d,\"quantity\":%d}",
			            userId, productId, quantity);
			 
			 
			 HttpEntity<String> entity =new HttpEntity<>(orderJson,headers);
			 
			 restTemplate.exchange(
			            "http://localhost:8086/orders",
			            org.springframework.http.HttpMethod.POST,
			            entity,
			            String.class
			        );
			 
			 
		}
		catch (Exception e) {
	        System.out.println("Failed to create order: " + e.getMessage());
	        throw new RuntimeException("Order creation failed");
	    }
	}

	
	
	
	
	
	
	
	
	
	

}
