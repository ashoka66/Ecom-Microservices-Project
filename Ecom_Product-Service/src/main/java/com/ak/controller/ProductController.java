package com.ak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.dto.ProductRequestDto;
import com.ak.entitiy.Product;
import com.ak.service.IProductService;

@RestController
@RequestMapping("/products")

public class ProductController{
	
	@Autowired
	private IProductService prodserv;
	
	@PostMapping
	public Product add(@RequestBody ProductRequestDto dto) {
		return prodserv.addProduct(dto);
	}
	
	@GetMapping
	public List<Product> getAll(){
		return prodserv.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Product getById(@PathVariable Long id) {
		return prodserv.getProductById(id);
	}
	
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
		return prodserv.updateProduct(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		
		prodserv.deleteProduct(id);
		return "Product Deleted ";
	}
	
	@GetMapping("/products/debug")
	public Object debug(Authentication authentication) {
	    return authentication.getAuthorities();
	}
	
	
}