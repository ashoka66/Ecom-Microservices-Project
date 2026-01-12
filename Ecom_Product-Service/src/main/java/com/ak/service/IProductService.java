package com.ak.service;

import java.util.List;

import com.ak.dto.ProductRequestDto;
import com.ak.entity.Product;

public interface IProductService {
	
 public	Product addProduct(ProductRequestDto dto);
 
 public List<Product> getAllProducts();
 
 public Product getProductById(Long id);
 
 public Product updateProduct(Long id, ProductRequestDto dto);
 
 public void deleteProduct(Long id);
	
	

}
