package com.ak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/products/**")
		    		                           .hasAnyRole("USER", "ADMIN")
		    		                           
		    		                           
		    		                           //Admin only
		    		                           .requestMatchers(HttpMethod.POST,"/products/**")
		    		                           .hasRole("ADMIN")
		    		                           .requestMatchers(HttpMethod.PUT,"/products/**")
		    		                           .hasRole("ADMIN")
		    		                           .requestMatchers(HttpMethod.DELETE,"/products/**")
		    		                           .hasRole("ADMIN")
		    		                           .anyRequest().authenticated()
		    		                           
		    		                      
		    		
		    		     
		    		
		    		    );
		
		return http.build();
	}

}
