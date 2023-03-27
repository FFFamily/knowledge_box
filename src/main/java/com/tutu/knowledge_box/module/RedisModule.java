package com.tutu.knowledge_box.module;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisModule {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * set
     */
    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

}
