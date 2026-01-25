package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;


	@Configuration
	public class RequestContextConfig {

	    @Bean
	    public RequestContextListener requestContextListener() {
	        return new RequestContextListener();
	    }
	}

