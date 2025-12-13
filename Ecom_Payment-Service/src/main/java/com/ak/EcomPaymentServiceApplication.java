package com.ak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EcomPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomPaymentServiceApplication.class, args);
	}

}
