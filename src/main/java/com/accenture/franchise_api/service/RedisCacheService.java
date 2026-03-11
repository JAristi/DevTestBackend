package com.accenture.franchise_api.service;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface RedisCacheService {

    Mono<Void> save(String key, Object value);

    <T> Mono<T> get(String key, Class<T> clazz);

    Mono<Void> delete(String key);

}
