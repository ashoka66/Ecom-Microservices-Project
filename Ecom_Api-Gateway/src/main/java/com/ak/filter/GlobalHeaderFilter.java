package com.ak.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalHeaderFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		
		return -1; //execute before session validation filter
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("GlobalHeaderFilter - Headers :" +exchange.getRequest().getHeaders());
		return chain.filter(exchange);
	}

}
