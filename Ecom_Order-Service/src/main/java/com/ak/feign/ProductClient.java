package com.ak.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ak.dto.ProductResponse;

@FeignClient(name = "product-service")
public interface ProductClient {
	
	@GetMapping("/products/{id}")
	public ProductResponse getProduct(@PathVariable Long id);

}
