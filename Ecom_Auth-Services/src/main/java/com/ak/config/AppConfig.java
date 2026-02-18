package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {

        	 // Add internal service header
            request.getHeaders().add("X-Internal-Calls", "AUTH_SERVICE");
         //  ADDED DEBUG LOGGING
            System.out.println(" RestTemplate Request:");
            System.out.println("   URL : " + request.getURI());
            System.out.println("   Headers: " + request.getHeaders());
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
