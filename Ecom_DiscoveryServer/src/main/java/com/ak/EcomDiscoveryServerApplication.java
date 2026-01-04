package com.ak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EcomDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomDiscoveryServerApplication.class, args);
	}

	



}
