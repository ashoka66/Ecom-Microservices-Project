package com.ak.service;

import com.ak.entity.Cart;

public interface ICartService {
	
	Cart getCartByUserId(Long userId);
	
	Cart addUser(Long userId, Long productId, Integer quantity); //Integer for by default null
	
	Cart updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity);
	
	Cart removeFromCart(Long userId, Long cartItemId);
	
	void clearCart(Long userId);

}
