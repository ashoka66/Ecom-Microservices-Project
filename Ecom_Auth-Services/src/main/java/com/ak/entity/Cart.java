package com.ak.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	
	
	private Long id;
	
	//Each cart Belongs to one user
	private long userId;
	
	@OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items= new ArrayList<>();
	
	@Transient
	public Double getTotalPrice() {
		return items.stream()
				.mapToDouble(CartItem::getSubTotal)
				.sum();
	}
	
	

}
