package com.xiamu.demo.redis;

import com.xiamu.demo.juc.threadpool.CustomizeThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
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


    public void test() {
        CountDownLatch count = new CountDownLatch(2);
        String lockKey = "LOCK_KEY";
        CustomizeThreadPool.threadPool.execute(() -> {
            try {
                count.await();
                redissonLocker.lock2(lockKey);
                log.info(Thread.currentThread().getId() + "获取锁");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                redissonLocker.unlock(lockKey);
                log.info("线程1释放锁");
            }
        });
        count.countDown();
        CustomizeThreadPool.threadPool.execute(() -> {
            try {
                count.await();
                TimeUnit.SECONDS.sleep(2);
                redissonLocker.lock2(lockKey);
                log.info(Thread.currentThread().getId() + "获取锁");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                redissonLocker.unlock(lockKey);
                log.info("线程2释放锁");
            }
        });
        count.countDown();
    }

    public void test2() {
        String lockKey = "LOCK_KEY";
        CustomizeThreadPool.threadPool.execute(() -> {
            redissonLocker.lock2(lockKey);
            redissonLocker.lock2(lockKey);
            try {
                TimeUnit.SECONDS.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                redissonLocker.unlock(lockKey);
            }
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
