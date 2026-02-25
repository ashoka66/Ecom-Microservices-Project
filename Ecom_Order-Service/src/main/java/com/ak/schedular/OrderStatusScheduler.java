package com.ak.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ak.entity.Order;
import com.ak.enums.OrderStatus;
import com.ak.repository.OrderRepository;

//@Component
public class OrderStatusScheduler {
	
	@Autowired
    private OrderRepository orderRepository;
	
	
	/**
     * Auto-update order status based on time elapsed
     * Runs every 5 seconds
     */
	
	@Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void updateOrderStatuses() {
        
        LocalDateTime now = LocalDateTime.now();
        
        // PLACED → CONFIRMED (after 5 seconds)
        updateStatus(OrderStatus.PLACED, OrderStatus.CONFIRMED, now.minusSeconds(5));
        
        // CONFIRMED → PROCESSING (after 10 seconds total)
        updateStatus(OrderStatus.CONFIRMED, OrderStatus.PROCESSING, now.minusSeconds(10));
        
        // PROCESSING → OUT_FOR_DELIVERY (after 20 seconds total)
        updateStatus(OrderStatus.PROCESSING, OrderStatus.OUT_FOR_DELIVERY, now.minusSeconds(20));
        
        // OUT_FOR_DELIVERY → DELIVERED (after 30 seconds total)
        updateToDelivered(now.minusSeconds(30));
    }
	
	
	/**
     * Update orders from one status to another
     */
    private void updateStatus(OrderStatus fromStatus, OrderStatus toStatus, LocalDateTime before) {
        List<Order> orders = orderRepository.findByDeliveryStatusAndPlacedAtBefore(fromStatus, before);
        
        for (Order order : orders) {
            order.setDeliveryStatus(toStatus);
            orderRepository.save(order);
            System.out.println(" Order #" + order.getId() + " status updated: " + 
                             fromStatus + " → " + toStatus);
        }
    }
    
    /**
     * Update orders to DELIVERED and set delivery timestamp
     */
    private void updateToDelivered(LocalDateTime before) {
        List<Order> orders = orderRepository.findByDeliveryStatusAndPlacedAtBefore(
            OrderStatus.OUT_FOR_DELIVERY, before);
        
        for (Order order : orders) {
            order.setDeliveryStatus(OrderStatus.DELIVERED);
            order.setDeliveredAt(LocalDateTime.now());
            orderRepository.save(order);
            System.out.println("✅ Order #" + order.getId() + " DELIVERED at " + order.getDeliveredAt());
        }
    }

}
