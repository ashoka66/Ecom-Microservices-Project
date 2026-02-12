package com.ak.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ak.entity.Notification;
import com.ak.entity.User;
import com.ak.repository.UserRepository;
import com.ak.service.INotificationService;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private INotificationService notificationService;
	
	
	@GetMapping
	public String viewProfile(Model model, Principal principal) {
		
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()->new RuntimeException("user not found"));
		
		//get user notification
		List<Notification> notifications= notificationService.getUserNotifications(user.getId());
		Long unreadCount=notificationService.getUnreadCount(user.getId());
		
		 
		model.addAttribute("notifications", notifications);
		model.addAttribute("user",user);
		model.addAttribute("unreadCount",unreadCount);
		model.addAttribute("username",principal.getName());
		
		return "profile";
	
	}
	
	//marks notifications as read 
	@PostMapping("/nofitications/{id}/read")
	public String marksAsRead(@PathVariable Long id) {
		
		notificationService.marksAsRead(id);
		return "redirect:/profile";
	}
	
	//marks all notification as read
	@PostMapping("/notifications/read-all")
	public String marksAllasRead(Principal principal) {
		
		User user=userRepository.findByEmail(principal.getName())
				.orElseThrow(()->new RuntimeException("user not Found"));
		
		notificationService.marksAllasRead(user.getId());
		
		return "redirect:/profile";
		
	}
	
	
	
	
	

}
