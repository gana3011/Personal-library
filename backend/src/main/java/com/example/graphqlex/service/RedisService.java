package com.example.graphqlex.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> template;

    public void save(String key, Object value, long timeoutInMinutes) {
        template.opsForValue().set(key, value, Duration.ofMinutes(timeoutInMinutes));
    }

    public Object get(String key) {
        return template.opsForValue().get(key);
    }

    public void delete(String key) {
        template.delete(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }
}
