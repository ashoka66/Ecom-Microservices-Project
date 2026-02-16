package com.ak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart ;
	
	
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productImageUrl;
    private Integer quantity;
    
    
    
    //calculate sub total for this item
//    @Transient
    
    public Double getSubTotal() {
    	
    	
    	 if(productPrice==null || quantity == null) {
    		 return 0.0;
    	 }
    	return productPrice * quantity;
    }
	
	

}
