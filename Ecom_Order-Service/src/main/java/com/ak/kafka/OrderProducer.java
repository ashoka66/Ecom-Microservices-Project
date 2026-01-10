package com.ak.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ak.entity.Order;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProducer {
	
	private final KafkaTemplate<String,Object> kafkaTemplate;
	
	public void sendOrderEvent(Order order) {
		kafkaTemplate.send("order-created",order);
	}

}
