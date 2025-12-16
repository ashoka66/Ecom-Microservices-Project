package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
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
	
//}

	@Bean
    public SecurityWebFilterChain springsSecurityFilterChain(ServerHttpSecurity http) {
        
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(ex->ex
                .anyExchange().permitAll()  // Let our custom filter handle auth
            );
        
        return http.build();
    }
}
