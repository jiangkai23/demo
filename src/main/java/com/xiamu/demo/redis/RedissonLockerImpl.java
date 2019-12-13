package com.xiamu.demo.redis;

import org.redisson.api.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * @date 2019-08-02
 */
@Service
public class RedissonLockerImpl implements RedissonLocker {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public void lock2(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }
    
    @Override
    public RLock lock(String lockKey, TimeUnit unit ,int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock();
    }
    
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }
    
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }
    
    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

    @Override
    public RBloomFilter<String> createBloomFilter(String name, long capacity, double errorRate) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(name);
        bloomFilter.tryInit(capacity, errorRate);
        return bloomFilter;
    }

    @Override
    public RBloomFilter<String> getBloomFilter(String name) {
        return redissonClient.getBloomFilter(name);
    }

    @Override
    public RRateLimiter createRateLimiter(String name, long rate, long rateInterval, RateIntervalUnit rateIntervalUnit) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(name);
        rateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, rateIntervalUnit);
        return rateLimiter;
    }

    @Override
    public RRateLimiter getRateLimiter(String name) {
        return redissonClient.getRateLimiter(name);
    }
}