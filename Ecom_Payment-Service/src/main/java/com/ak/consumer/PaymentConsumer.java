package com.ak.consumer;



import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.ak.dto.OrderEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentConsumer {

    @KafkaListener(
        topics = "order-created",
        groupId = "payment-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderEvent(Message<OrderEvent> message ) {
    	
    	  OrderEvent event = message.getPayload();

        log.info("Received Order Event -> {}", event);

        // simulate payment processing
        log.info("Processing payment for orderId = {}", event.getId());

        try { Thread.sleep(2000); } catch (Exception ignored) {}

        log.info("Payment completed for order {}", event.getId());
    }
    
   
}
