package com.xiamu.demo.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author XiaMu
 * @date 2018-09-24
 */
@Slf4j
@Component
public class CuratorLock {

    @Resource
    private CuratorFramework curatorClient;


    /**
     * curator获取锁
     */
    public InterProcessMutex getCuratorLock(String path) {
        return new InterProcessMutex(curatorClient, path);
    }

    /**
     * curator方式加锁
     * @param lock 锁
     */
    public void curatorLock(InterProcessMutex lock) {
        try {
            lock.acquire();
            log.info("{}获取锁成功", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * curator方式释放锁
     * @param lock 锁
     */
    public void curatorReleaseLock(InterProcessMutex lock) {
        if (null != lock && lock.isAcquiredInThisProcess()) {
            try {
                lock.release();
                log.info("{}释放锁成功", Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
