package com.accenture.franchise_api.service.Impl;

import com.accenture.franchise_api.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements RedisCacheService {

    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> Mono<T> get(String key, Class<T> clazz) {
        return redisTemplate.opsForValue()
                .get(key)
                .cast(clazz)
                .doOnNext(value -> log.debug("Cache hit: {}", key))
                .onErrorResume(e -> {
                    log.warn("Redis no disponible (get): {}", e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> delete(String key) {
        return redisTemplate.opsForValue()
                .delete(key)
                .doOnNext(deleted -> {
                    if (deleted) {
                        log.debug("Caché eliminada: {}", key);
                    }
                })
                .onErrorReturn(false);
    }
}