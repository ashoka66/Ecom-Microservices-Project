package com.ak.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ak.entity.Notification;


public interface INotificationService {
	
	//create a new notification for user
	Notification createNotification(Long userId, String message, String type);
	
	//get all notifications for a user
	List<Notification> getUserNotifications(Long userId);
	
	
	//get unread notification count
	Long getUnreadCount(Long UserId);
	
	public void marksAsRead(Long notificationId);
	
	
	//marks all notification as read for a user
	public void marksAllasRead(Long userId);
	
	
	
	

}
