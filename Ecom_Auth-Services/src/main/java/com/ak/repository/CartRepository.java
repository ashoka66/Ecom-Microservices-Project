package com.ak.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
	
	Optional<Cart> findByUserId(Long UserId);

}
