package com.ak.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	
	Optional<CartItem> findByIdAndProductId(Long cartId, Long productId);
	

}
