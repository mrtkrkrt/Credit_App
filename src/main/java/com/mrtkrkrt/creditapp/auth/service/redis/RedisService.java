package com.mrtkrkrt.creditapp.auth.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public T getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void putValue(String key, TimeUnit expirationTimeUnit, int expirationTime, T value) {
        redisTemplate.opsForValue().set(key, value, expirationTime, expirationTimeUnit);
    }
}
