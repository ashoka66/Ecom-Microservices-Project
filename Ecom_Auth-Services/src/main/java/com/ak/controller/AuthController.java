package com.ak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.service.IUserService;

import dto.UserRequestDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private IUserService useservice;
	
	@PostMapping("/register")
	public String register(@RequestBody UserRequestDto dto) {
		
		useservice.register(dto);
		return "User registered Successfully";
	}
	
	
}
