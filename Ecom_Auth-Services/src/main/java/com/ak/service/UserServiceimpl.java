package com.ak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ak.entity.User;
import com.ak.repository.UserRepository;

import dto.UserRequestDto;


@Service
public class UserServiceimpl implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder  passwordEncoder;

	@Override
	public void register(UserRequestDto dto) {
		
		User user=new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole("USER");
		
		userRepo.save(user);
	
	}

	
}
