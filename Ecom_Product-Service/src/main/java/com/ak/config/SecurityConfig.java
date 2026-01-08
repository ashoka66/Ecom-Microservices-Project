package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j

public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		log.info("Initializing Spring Security filter chain configuration");

		
		http.csrf(csrf -> csrf.disable())
		     .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
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
		
		log.info("Spring Security filter chain configured successfully");

		return http.build();
	}

}
