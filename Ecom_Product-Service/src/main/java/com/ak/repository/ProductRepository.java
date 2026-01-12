package com.ak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
