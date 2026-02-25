package com.ak.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ak.dto.OrderRequestDto;
import com.ak.dto.ProductResponse;
import com.ak.entity.Order;
import com.ak.enums.OrderStatus;
import com.ak.feign.ProductClient;
import com.ak.kafka.OrderProducer;
import com.ak.repository.OrderRepository;

import lombok.RequiredArgsConstructor;



/**
 * Order Service Implementation Handles order placement and business logic
 * Integrates with Product Service (Feign) and Kafka for events
 */

@Service
@RequiredArgsConstructor
public class OrderServiceimpl implements IOrderService {
	
	
	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final OrderProducer orderProducer;
	
	
	@Override
	public Order placeOrder(OrderRequestDto dto) {
		
		//1 .fetch product details
		ProductResponse product= productClient.getProduct(dto.getProductId());
		
		if(product == null || product.getStock() < dto.getQuantity()) {
			
			throw new RuntimeException("product stock not sufficient");
		}
		
		
		//2.calculate total price 
		double totalPrice = product.getPrice() * dto.getQuantity();
		
		//3. Create Order
		Order order = new Order();
		order.setUserId(dto.getUserId());
		order.setProductId(dto.getProductId());
		order.setQuantity(dto.getQuantity());
		order.setTotalPrice(totalPrice);
		
		
		 //  Set initial status and timestamp
        order.setDeliveryStatus(OrderStatus.PLACED);
        order.setPlacedAt(LocalDateTime.now());
        
        Order savedOrder = orderRepository.save(order);
        
      //4. publish kafka event
		orderProducer.sendOrderEvent(savedOrder);
        
        System.out.println("✅ Order created with ID: " + savedOrder.getId() + 
                         " | Status: " + savedOrder.getDeliveryStatus());
        
        sendNotificatioToUser(dto.getUserId(),
				"order #" +savedOrder.getId() + "placed sucessfully Total " + totalPrice);
        
        return savedOrder;
		
		
	}


	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}
	
	
	
	
	
	private void sendNotificatioToUser(Long userId, String message) {
		
		try {
			
			RestTemplate restTemplate= new RestTemplate();
			
			String notificationJson = String.format("{\"userId\":%d,\"message\":\"%s\",\"type\":\"ORDER_PLACED\"}", userId,message);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("X-Internal-Calls", "AUTH-SERVICE");
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity=new HttpEntity<>(notificationJson,headers);
			
			restTemplate.postForObject("http://localhost:8081/api/notifications", entity, String.class);
			
			System.out.println("Notification send to user " + userId);
			
 		}
		catch(Exception e) {
			
			System.out.println("Failed to send Notifications"+e.getMessage());
			//don't fail the notification if notification fails 
		}
		
	}
	
	
	

}
