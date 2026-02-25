package com.ak.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.Order;
import com.ak.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByUserId(Long userId);
	
	//  NEW: Find orders by status
    List<Order> findByDeliveryStatus(OrderStatus status);
    
 // NEW: Find orders placed before a certain time with specific status
    List<Order> findByDeliveryStatusAndPlacedAtBefore(OrderStatus status, LocalDateTime dateTime);

}
