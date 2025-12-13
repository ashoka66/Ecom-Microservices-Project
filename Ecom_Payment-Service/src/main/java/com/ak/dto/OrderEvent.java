package com.ak.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEvent {
	
	private Long Id;
	
	private Long userId;
	
	private Long productId;
	
	private Double totalPrice;
	
	private Integer quantity;
	
	private String status;

}
