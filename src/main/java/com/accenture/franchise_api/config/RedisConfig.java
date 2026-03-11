package com.accenture.franchise_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {

        RedisSerializationContext<String, Object> context =
                RedisSerializationContext
                        .<String, Object>newSerializationContext(new StringRedisSerializer())
                        .value(new GenericJackson2JsonRedisSerializer())
                        .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
