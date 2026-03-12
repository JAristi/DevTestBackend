package com.accenture.franchise_api.service;

import reactor.core.publisher.Mono;

public interface RedisCacheService {


    <T> Mono<T> get(String key, Class<T> clazz);

    Mono<Boolean> delete(String key);

}
