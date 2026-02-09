package com.ak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ak.entity.Cart;
import com.ak.repository.CartRepository;
import com.ak.repository.NotificationRepository;
import com.ak.repository.UserRepository;

@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	

	@Override
	public Cart getCartByUserId(Long userId) {
		return	cartRepository.findByUserId(userId).orElseGet(() -> {
			
			Cart newCart=new Cart();
			newCart.setUserId(userId);
			return cartRepository.save(newCart);
			
					
			
		});
		
	}

	@Override
	public Cart addUser(Long userId, Long productId, Integer quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart removeFromCart(Long userId, Long cartItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearCart(Long userId) {
		// TODO Auto-generated method stub

	}

}
