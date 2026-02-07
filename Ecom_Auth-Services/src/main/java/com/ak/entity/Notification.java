package com.ak.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
	
	private Long id;
	
	private Long userId;
	
	private String message;
	
	private String type;  //ORDER_PLACED, ORDER_COMPLETED, SYSTEM, etc.
	
	private Boolean isRead = false ;
	
	private LocalDateTime createdAt;
	
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

}
