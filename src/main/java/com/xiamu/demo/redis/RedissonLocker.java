package com.xiamu.demo.redis;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;

/**
 * @author XiaMu
 * @date 2019-08-02
 */
public interface RedissonLocker {

    /*
     * 注意:如果设置了锁超时时间则不会使用watchDog;
     * 默认情况下锁定看门狗超时为30秒,可以通过Config.lockWatchdogTimeout设置进行更改。
     * watchDog会在过期时间1/3时检测业务有没有执行完,如果没有则续租到开始的超时时间
     * 如果不手动释放锁,watchDog会一直对其续租,崩溃时不会续租防止死锁
     * 可重入锁,如果多次加锁一定要多次释放
     */


    /**
     * 加锁返回锁
     * @param lockKey 加锁key
     * @return 返回锁
     */
    RLock lock(String lockKey);

    /**
     * 没有返回值的获取锁
     * @param lockKey 加锁key
     */
    void lock2(String lockKey);

    /**
     * 超时加锁返回锁
     * @param lockKey 加锁key
     * @param leaseTime 锁时间
     * @return 锁
     */
    RLock lock(String lockKey, int leaseTime);

    /**
     * 设置单位超时加锁返回锁
     * @param lockKey 加锁key
     * @param unit 时间单位
     * @param leaseTime 锁时间
     * @return 锁
     */
    RLock lock(String lockKey, TimeUnit unit, int leaseTime);

    /**
     * 获取锁
     * @param lockKey key
     * @return 获取结果
     */
    boolean tryLock(String lockKey);

    /**
     * 加锁
     * @param lockKey 加锁key
     * @param unit 时间单位
     * @param waitTime 加锁等待时间
     * @param leaseTime 锁时间
     * @return 是否成功
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * 通过lockKey释放锁
     * @param lockKey 加锁key
     */
    void unlock(String lockKey);

    /**
     * 解锁
     * @param lock 锁
     */
    void unlock(RLock lock);

    /**
     * 创建并获取布隆过滤器
     * @param name 名字
     * @param capacity 容量
     * @param errorRate 错误率
     * @return 布隆过滤器
     */
    RBloomFilter<String> createBloomFilter(String name, long capacity, double errorRate);

    /**
     * 获取布隆过滤器
     * @param name 名字
     * @return 布隆过滤器
     */
    RBloomFilter<String> getBloomFilter(String name);

    /**
     * 初始化并获取限流组件
     * @param name 组件名字
     * @param rate 速率
     * @param rateInterval 速率间隔
     * @param rateIntervalUnit 速率间隔单位
     * @return 限流组件
     */
    RRateLimiter createRateLimiter(String name, long rate, long rateInterval, RateIntervalUnit rateIntervalUnit);

    /**
     * 获取限流组件
     * @param name 组件名字
     * @return 限流组件
     */
    RRateLimiter getRateLimiter(String name);

}