package com.ak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ak.dto.ProductRequestDto;
import com.ak.entity.Product;
import com.ak.repository.ProductRepository;


@Service
public class ProductServiceimpl implements IProductService {
	
	@Autowired
	private ProductRepository prodRepo;
	

	@Override
	@CacheEvict(value="products", allEntries=true)
	public Product addProduct(ProductRequestDto dto) {
		Product p=new Product();
		
		
		
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setStock(dto.getStock());
		p.setCategory(dto.getCategory());
		return prodRepo.save(p);
	}

	@Override
	@Cacheable(value = "products")
	public List<Product> getAllProducts() {
		
		return prodRepo.findAll();
	}

	@Override
	@Cacheable(value="product",key="#id")
	public Product getProductById(Long id) {
		
		return prodRepo.findById(id).orElse(null);
	}

	@Override
	@CacheEvict(value= {"Products","Product"},allEntries=true)
	public Product updateProduct(Long id, ProductRequestDto dto) {
		
		Product p = prodRepo.findById(id).orElseThrow();
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setStock(dto.getStock());
		p.setCategory(dto.getCategory());
		
		return prodRepo.save(p);
	}

	@Override
	@CacheEvict(value= {"Products","prodcut"},allEntries=true)
	public void deleteProduct(Long id) {
		prodRepo.deleteById(id);
		
	}

}
