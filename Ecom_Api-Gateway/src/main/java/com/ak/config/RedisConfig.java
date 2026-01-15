package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import org.springframework.data.redis.serializer.StringRedisSerializer;


//@Configuration
//public class RedisConfig {
//	 @Bean 
//	    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
//	            ReactiveRedisConnectionFactory connectionFactory) {
//	        
//	        RedisSerializationContext<String, String> serializationContext = 
//	            RedisSerializationContext.<String, String>newSerializationContext(new StringRedisSerializer())
//	                .build();
//	        
//	        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
//	    }
//}



//Marks this class as a Spring configuration class.
//Spring will scan this class and execute all @Bean methods inside it.
//@Configuration
public class RedisConfig {

 // @Bean tells Spring:
 // "The object returned from this method should be created once
 //  and managed inside the Spring Application Context."
 @Bean 
 public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(

         // Spring automatically injects this parameter.
         // ReactiveRedisConnectionFactory is responsible for:
         // - Creating TCP connections to Redis
         // - Managing connection pooling
         // - Handling non-blocking (reactive) communication
         // You NEVER create this manually.
         ReactiveRedisConnectionFactory connectionFactory
 ) {

     // Redis does NOT store Java objects.
     // It stores raw bytes (byte[]).
     // So we must define HOW Java objects are converted to bytes
     // and back again. This is called "serialization".

     // RedisSerializationContext defines:
     // - How keys are serialized
     // - How values are serialized
     //
     // <String, String> means:
     // - Redis key type   -> String
     // - Redis value type -> String
     RedisSerializationContext<String, String> serializationContext = 

         // This is a STATIC FACTORY METHOD (not normal 'new').
         // It starts building a RedisSerializationContext using
         // the provided serializer.
         //
         // <String, String> explicitly tells Java:
         // "Use String for both key and value types".
         RedisSerializationContext.<String, String>
             newSerializationContext(

                 // StringRedisSerializer converts:
                 // - Java String -> UTF-8 bytes (before storing in Redis)
                 // - UTF-8 bytes -> Java String (when reading from Redis)
                 //
                 // This keeps Redis data:
                 // - Human readable
                 // - Debuggable using redis-cli
                 new StringRedisSerializer()
             )

             // Finalizes the builder and creates an immutable
             // RedisSerializationContext object.
             .build();

     // Create the ReactiveRedisTemplate instance.
     //
     // This template:
     // - Uses the provided Redis connection factory
     // - Uses the defined serialization rules
     // - Provides non-blocking Redis operations
     // - Is fully compatible with Spring WebFlux and API Gateway
     return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
 }
}
