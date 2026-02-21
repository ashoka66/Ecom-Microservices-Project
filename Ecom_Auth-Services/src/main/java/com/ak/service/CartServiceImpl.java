package com.ak.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ak.entity.Cart;
import com.ak.entity.CartItem;
import com.ak.repository.CartItemRepository;
import com.ak.repository.CartRepository;
import com.ak.repository.NotificationRepository;
import com.ak.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	

	@Override
	public Cart getCartByUserId(Long userId) {
		return	cartRepository.findByUserId(userId).orElseGet(() -> {
			
			Cart newCart=new Cart();
			newCart.setUserId(userId);
			return cartRepository.save(newCart);
			
					
			
		});
		
	}

	@Override
	@Transactional
	public Cart addUser(Long userId, Long productId, Integer quantity) {
		
		Cart cart = getCartByUserId(userId);
		
		String productUrl= "http://localhost:8086/products/" + productId;
		
		try {
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("X-Internal-Calls", "AUTH_SERVICE");
		
		HttpEntity<String> entity=new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(productUrl, HttpMethod.GET, entity, String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		Map<String,Object> product=mapper.readValue(response.getBody(), new TypeReference<Map<String,Object>>(){});
		
		//check if parduct already  exist in the cart
		Optional<CartItem> existingItem=cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
		
		if(existingItem.isPresent()) {
			CartItem item=existingItem.get();
			
			item.setQuantity(item.getQuantity() + quantity);
			cartItemRepository.save(item);
		}
		else {
			CartItem newItem = new CartItem();
			
			newItem.setCart(cart);
			newItem.setProductId(productId);
			newItem.setProductName((String) product.get("name")); // return the value based on the key
			newItem.setProductImageUrl((String) product.get("ImageUrl"));
			newItem.setProductPrice((Double) product.get("price"));
			newItem.setQuantity(quantity);
			
			cartItemRepository.save(newItem);  
			
			
		}
		
		return cartRepository.findById(cart.getId()).orElse(cart);
		
		}
		catch(Exception e) {
			
			System.out.println("Error Adding to cart" + e.getMessage());
			throw new RuntimeException("Failed to add product to cart" + e.getMessage());
		}
	}
	
	//update cart item of existing cart item 

	@Override
	@Transactional
	public Cart updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
		
		Cart cart =getCartByUserId(userId);
		
		CartItem item=cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("Cart Item not found"));
		
		
		//verify item belongs to cart
		if(!item.getCart().getId().equals(cart.getId())) {
			throw new RuntimeException("Unauthorized cart item access");
			
		}
		
		if(quantity <=0) {
			cartItemRepository.delete(item);
			
		}
		else {
			item.setQuantity(quantity);
			cartItemRepository.save(item);
		}
		
		return cartRepository.findById(cart.getId()).orElse(cart);
	}
	
    
	
	// Removing items from cart 
	@Override
	@Transactional
	public Cart removeFromCart(Long userId, Long cartItemId) {
		
		Cart cart= getCartByUserId(userId);  //this is optional object where we are handling null pointer Exception
		
		CartItem item=cartItemRepository.findById(cartItemId)
				       .orElseThrow(() -> new RuntimeException("Cart item not found"));
		
	  //verify cart item belongs to user'cart
		if(!item.getCart().getId().equals(cart.getId())) {
			
			throw new RuntimeException("unauthorized cart item access");
			
		}
		
		cartItemRepository.delete(item);
		
		return cartRepository.findById(cart.getId()).orElse(cart);
	}

	@Override
	@Transactional
	public void clearCart(Long userId) {
		
		Cart cart=getCartByUserId(userId);
		
		cart.getItems().clear();
		
		cartRepository.save(cart);
		
		
	}

}
