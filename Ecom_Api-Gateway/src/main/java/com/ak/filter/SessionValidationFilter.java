package com.ak.filter;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
@Order(1)
public class SessionValidationFilter implements WebFilter {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        
        String path = exchange.getRequest().getPath().value();
        
        // Allow auth endpoints without session validation
        if (path.startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        // For protected endpoints (/products, /orders), validate session
        if (path.startsWith("/products") || path.startsWith("/orders")) {
            
            HttpCookie sessionCookie = exchange.getRequest().getCookies().getFirst("JSESSIONID");
            
            if (sessionCookie == null) {
                System.out.println("❌ No JSESSIONID cookie found");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String encodedSessionId = sessionCookie.getValue();
            System.out.println("🔍 Encoded Session ID: " + encodedSessionId);
            
            // Decode Base64 session ID
            String decodedSessionId = decodeSessionId(encodedSessionId);
            System.out.println("🔓 Decoded Session ID: " + decodedSessionId);
            
            return validateSession(decodedSessionId)
                .flatMap(isValid -> {
                    if (Boolean.TRUE.equals(isValid)) {
                        System.out.println("✅ Session valid, proceeding...");
                        return chain.filter(exchange);
                    } else {
                        System.out.println("❌ Session invalid or expired");
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                });
        }

        return chain.filter(exchange);
    }

    private String decodeSessionId(String encodedSessionId) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedSessionId);
            return new String(decodedBytes);
        } catch (Exception e) {
            System.out.println("⚠️ Session ID is not Base64 encoded, using as-is");
            return encodedSessionId;
        }
    }

    private Mono<Boolean> validateSession(String sessionId) {
        String redisKey = "spring:session:sessions:" + sessionId;
        System.out.println("🔍 Checking Redis key: " + redisKey);
        
        return reactiveRedisTemplate.hasKey(redisKey)
            .doOnNext(exists -> {
                if (Boolean.TRUE.equals(exists)) {
                    System.out.println("✅ Session found in Redis!");
                } else {
                    System.out.println("❌ Session NOT found in Redis!");
                }
            })
            .defaultIfEmpty(false);
    }
}