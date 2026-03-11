package com.accenture.franchise_api.service.Impl;

import com.accenture.franchise_api.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> save(String key, Object value) {

        return redisTemplate
                .opsForValue()
                .set(key, value)
                .then();
    }

    @Override
    public <T> Mono<T> get(String key, Class<T> clazz) {

        return redisTemplate
                .opsForValue()
                .get(key)
                .cast(clazz);
    }

    @Override
    public Mono<Void> delete(String key) {

        return redisTemplate
                .opsForValue()
                .delete(key)
                .then();
    }
}
