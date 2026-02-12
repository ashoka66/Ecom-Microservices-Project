package com.ak.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ak.entity.Cart;
import com.ak.entity.User;
import com.ak.repository.UserRepository;
import com.ak.service.ICartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private UserRepository userRepository;
	
	
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
			return "success";
		}
		catch(Exception e) {
			return "error" + e.getMessage();
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
	public String removeItem(@RequestParam Long cartItemId, Principal principal) {
		
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()->new RuntimeException("user not found"));
		
		cartService.removeFromCart(user.getId(), cartItemId);
		return "redirect:/cart";
                		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
