package com.ak.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.dto.OrderRequestDto;
import com.ak.dto.ProductResponse;
import com.ak.entity.Order;
import com.ak.feign.ProductClient;
import com.ak.kafka.OrderProducer;
import com.ak.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

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
		
		if(product == null || product.getStock() <= dto.getQuantity()) {
			
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
		order.setStatus("CREATED");
		
		Order saveOrder = orderRepository.save(order);
		
		//4. publish kafka event
		orderProducer.sendOrderEvent(saveOrder);
		
		return saveOrder;
	}


	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

}
