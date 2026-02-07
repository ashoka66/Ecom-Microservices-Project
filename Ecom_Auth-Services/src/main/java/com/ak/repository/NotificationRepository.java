package com.ak.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	//get all notification fir a specific user
	//Ordered by creation date (newest first)
	List<Notification> findByUserIdOrderByrCreatedAtDesc(Long userId);
	
	
	//get unread notification count for a user
	Long countByUserIdAndIsReadFalse(Long userId);

}
