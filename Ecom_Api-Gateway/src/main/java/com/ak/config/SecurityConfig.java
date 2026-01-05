package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	
//	@Bean
//	public SecurityWebFilterChain springsSecurityFilterChain(ServerHttpSecurity http) {
//		
//		 http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//		     .authorizeExchange(ex->ex
//		    		             .pathMatchers("/auth/**").permitAll()
//		    		             .pathMatchers("/products/**").authenticated()
//		    		             .pathMatchers("/orders/**").authenticated()
//		    		             .anyExchange().authenticated()
//		    		             );
//		
//		
//		
//		
//		return http.build();
//	
//}
//
	
	//let the auth- service handle the requests
	@Bean
    public SecurityWebFilterChain springsSecurityFilterChain(ServerHttpSecurity http) {
        
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(ex->ex
                .anyExchange().permitAll()  // Let our custom filter handle auth
            );
        
        return http.build();
    }
}
