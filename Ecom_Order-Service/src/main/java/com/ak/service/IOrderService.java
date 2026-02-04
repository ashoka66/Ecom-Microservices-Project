package com.ak.service;

import java.util.List;

import com.ak.dto.OrderRequestDto;
import com.ak.entity.Order;

public interface IOrderService {
	
	public Order placeOrder(OrderRequestDto dto);
	
	public List<Order> getAllOrders();

}
