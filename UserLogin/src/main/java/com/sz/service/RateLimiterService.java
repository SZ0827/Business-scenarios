package com.sz.service;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class RateLimiterService {


    private final StringRedisTemplate redisTemplate;
    private final int MAX_ATTEMPTS = 5;  // 允许 5 次请求
    private final long TIME_WINDOW = 60; // 60 秒内

    public RateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String ip) {
        String key = "LOGIN_ATTEMPTS:" + ip;
        String count = redisTemplate.opsForValue().get(key);

        if (count == null) {
            redisTemplate.opsForValue().set(key, "1",  Duration.ofSeconds(30));
            return true;
        }

        int attempts = Integer.parseInt(count);
        if (attempts >= MAX_ATTEMPTS) {
            return false;
        }

        redisTemplate.opsForValue().increment(key);
        return true;
    }
}
