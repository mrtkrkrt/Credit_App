package com.mrtkrkrt.creditapp.auth.filter.service.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthRedisService<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public T getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void putValue(String key, TimeUnit expirationTimeUnit, int expirationTime, T value) {
        redisTemplate.opsForValue().set(key, value, expirationTime, expirationTimeUnit);
    }
}
