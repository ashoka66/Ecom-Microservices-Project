package com.ak.entity;

import java.time.LocalDateTime;

import com.ak.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor //it is for best coder standards
@AllArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;
	
	private Long productId;
	
	private Integer quantity;
	
	private Double totalPrice;
	
	/*
	 * private String status; //created, payment_pending, paid , failed
	 */	
	
	 //  NEW FIELDS
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private OrderStatus deliveryStatus = OrderStatus.PLACED;
    
    @Column(name = "placed_at")
    private LocalDateTime placedAt;
    
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    
    // Set placedAt automatically when order is created
    @PrePersist
    protected void onCreate() {
        if (placedAt == null) {
            placedAt = LocalDateTime.now();
        }
        if (deliveryStatus == null) {
            deliveryStatus = OrderStatus.PLACED;
        }
    }
	
	
	

}
