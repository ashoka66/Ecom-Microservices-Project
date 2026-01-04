package com.ak.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.dto.OrderRequestDto;
import com.ak.entity.Order;
import com.ak.service.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	
	
	private final IOrderService orderService; 
	
	@PostMapping
	public Order  placeOrder(@RequestBody OrderRequestDto dto) {
		
		
		
		
		return orderService.placeOrder(dto);
	}

}
