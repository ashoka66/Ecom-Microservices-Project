package com.ak.service;

import com.ak.dto.OrderRequestDto;
import com.ak.entity.Order;

public interface IOrderService {
	
	public Order placeOrder(OrderRequestDto dto);

}
