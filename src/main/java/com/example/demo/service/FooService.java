package com.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FooService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void foo() {
        redisTemplate.opsForValue().get("abc");
    }
}
