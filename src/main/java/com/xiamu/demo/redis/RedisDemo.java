package com.xiamu.demo.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * @date 2019-08-05
 */
@Component
public class RedisDemo {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public void test() {
        stringRedisTemplate.opsForValue().setIfAbsent("lockDemo", "lockValue", 20, TimeUnit.SECONDS);
    }


}
