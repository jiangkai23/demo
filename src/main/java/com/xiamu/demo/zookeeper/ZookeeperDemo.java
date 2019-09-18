package com.xiamu.demo.zookeeper;

import com.xiamu.demo.juc.CustomizeThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * @date 2019-07-25
 */
@Slf4j
@Component
public class ZookeeperDemo {

    @Resource
    private CuratorFramework zooKeeperClient;

    private String path = "/lock";

    private CountDownLatch countDownLatch = new CountDownLatch(2);

    public void test() {
        InterProcessMutex lock = new InterProcessMutex(zooKeeperClient, path);
        this.doSomeThing(lock);
        this.doSomeThing(lock);
    }

    private void doSomeThing(InterProcessMutex lock) {
        CustomizeThreadPool.threadPool.execute(() -> {
            try {
                countDownLatch.await();
                this.getLock(lock);
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.releaseLock(lock);
        });
        countDownLatch.countDown();
    }

    private void getLock(InterProcessMutex lock) {
        try {
            lock.acquire();
            log.info("{}获取锁成功", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseLock(InterProcessMutex lock) {
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
