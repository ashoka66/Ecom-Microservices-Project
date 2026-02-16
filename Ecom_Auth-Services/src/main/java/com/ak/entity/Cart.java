package com.ak.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//Each cart Belongs to one user
	@Column(unique=true)
	private long userId;
	
	@OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items= new ArrayList<>();
	
//	@Transient
	//calcualte sub total from this method 
	//commented transient and it is public so that thymleaf can access it 
	public Double getTotalPrice() {
		
		 if (items == null || items.isEmpty()) {
	            return 0.0;
	        }
		return items.stream()
				.mapToDouble(CartItem::getSubTotal)
				.sum();
	}
	
	

}
