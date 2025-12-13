package com.ak.dto;

import lombok.Data;

@Data
public class ProductRequestDto {
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private Integer stock;
	
	private String category;

}
