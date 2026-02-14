package com.ak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.service.INotificationService;



/*Notification Api Controller
Rest Api for Creating notifications from other services 
used by order servcie to notify user*/



@RestController
@RequestMapping("/api/notifications")
public class NotificaitonApiController {
	
	
	@Autowired
	private INotificationService notificationService;
	
	//creating notfication endpoint
	//called by other microservices (order service, payment servcies, etc)
	
	public String createNotification(@RequestBody NotificationRequest request) {
		
		try {
			notificationService.createNotification( request.getUserId(),request.getMessage(),request.getType());
			
			return "Notification created sucessfully";
		}
		catch(Exception e) {
			return "Error creating notification" + e.getMessage();
		}
	}
	
	//DTO for notification creation
	
	
	public static class NotificationRequest {
		
		private Long userId;
		private String message;
		private String type;
		
		public void setUserId(Long userId) {
			this.userId=userId;
		}
		
		public Long getUserId() {
			return userId;
		}
		
		
		public void setMessage(String message) {
			this.message=message;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setType(String type) {
			this.type=type;
		}
		
		public String getType() {
			return type;
		}
		
	}
	
	
	

}
