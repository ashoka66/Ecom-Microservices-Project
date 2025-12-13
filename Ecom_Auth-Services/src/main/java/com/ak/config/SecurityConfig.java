package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		
		http.csrf(csrf->csrf.disable())
		
		 //Allow session based auth 
		
		 // Allow session-based auth
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        )
		

		//url  security rules
        .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register").permitAll()
        		                            .requestMatchers("/auth/login").permitAll()
        		                             .anyRequest().authenticated())
        
         //Disable default login form
        .formLogin(form->form.disable())
        
        //Disable basic auth popup
        .httpBasic(basic->basic.disable());
	
	 return http.build();
	 
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
