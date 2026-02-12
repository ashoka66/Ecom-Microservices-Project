package com.ak.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ak.entity.Notification;
import com.ak.repository.NotificationRepository;

public class NotificationServiceImpl implements INotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Notification createNotification(Long userId, String message, String type) { //create new notification
		
		Notification notification= new Notification();
		notification.setUserId(userId);
		notification.setMessage(message);
		notification.setType(type);
		notification.setIsRead(false);
		
		return notificationRepository.save(notification);
	}
	
	
	//get all notifications for user (newest first)ss

	@Override
	public List<Notification> getUserNotifications(Long userId) {
		
		return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
	}

	 //count unread notification
	@Override
	public Long getUnreadCount(Long UserId) {
		
		return notificationRepository.countByUserIdAndIsReadFalse(UserId);
	}
	
	
	
	
	//mark single notification as read 
	@Override
	public void marksAsRead(Long notificationId) {
		
		notificationRepository.findById(notificationId)
		                      .ifPresent(notification -> {
		                    	  notification.setIsRead(true);
		                    	  notificationRepository.save(notification);
		                      });
		
		
	}
	
	
	
	//marks all user notification as read 

	@Override
	public void marksAllasRead(Long userId) {
		
		List<Notification> notifications=notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
		
		notifications.forEach(notification -> {
			notification.setIsRead(true);
			
			notificationRepository.save(notification);
			
		});
		
	}

}
