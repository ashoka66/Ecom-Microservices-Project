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

            ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attrs != null) {
                HttpServletRequest currentRequest = attrs.getRequest();
                String cookieHeader = currentRequest.getHeader("Cookie");
//                System.out.println("🔍 Incoming Cookie header: " + cookieHeader);

                if (cookieHeader != null) {
                    request.getHeaders().add("Cookie", cookieHeader);
                }
            }

            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
