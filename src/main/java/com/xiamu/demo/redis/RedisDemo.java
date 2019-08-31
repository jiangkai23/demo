package com.xiamu.demo.redis;

import com.xiamu.demo.juc.CustomizeThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * @date 2019-08-05
 */
@Component
@Slf4j
public class RedisDemo {

    @Resource
    private RedissonLocker redissonLocker;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void test() throws InterruptedException {
        String lockKey = "LOCK_KEY";
        String value = UUID.randomUUID().toString();
        boolean result = lock(lockKey, value);
        boolean result1 = lock(lockKey, UUID.randomUUID().toString());
        log.info("获取分布式结果:{}", result);
        log.info("获取分布式结果:{}", result1);
        // doSomeThing()
        TimeUnit.SECONDS.sleep(5);
        result = luaUnLock(lockKey, value);
        log.info("释放分布式结果:{}", result);
        CustomizeThreadPool.threadPool.execute(() -> {
            redissonLocker.lock2(lockKey);
            log.info("1111获取锁成功");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redissonLocker.unlock(lockKey);
            log.info("1111释放锁");
        });

        CustomizeThreadPool.threadPool.execute(() -> {
            redissonLocker.lock2(lockKey);
            log.info("2222获取锁成功");
            redissonLocker.unlock(lockKey);
            log.info("2222释放锁");
        });
    }


    /**
     * 带有过期时间的setNx实现分布式锁,value保证不会释放锁其他人的锁
     */
    private Boolean lock(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, 10L, TimeUnit.SECONDS);
    }

    /**
     * 判断value是否相等之后可能锁过期别其他线程加锁,此时会删除其他人的锁
     */
    private Boolean unLock(String key, String value) {
        String cacheValue = stringRedisTemplate.opsForValue().get(key);
        if (!value.equals(cacheValue)) {
            return false;
        }
        return stringRedisTemplate.delete(key);
    }

    /**
     * 释放分布式锁安全,但是依然存在业务时间过长导致锁过期被其他线程获取的情况,此时需要检测续租锁可使用redisson
     */
    private Boolean luaUnLock(String key, String value) {
        ScriptSource lua = new ResourceScriptSource(new ClassPathResource("redisUnLock.lua"));
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(lua);
        redisScript.setResultType(Boolean.class);
        return stringRedisTemplate.execute(redisScript, Collections.singletonList(key), value);
    }

}
