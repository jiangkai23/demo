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
    private ZooKeeperLock zooKeeperLock;

    private String path = "/lock";

    private CountDownLatch countDownLatch = new CountDownLatch(2);

    public void test() {
        InterProcessMutex curatorLock = zooKeeperLock.getCuratorLock(path);
        this.doSomeThing(curatorLock);
        this.doSomeThing(curatorLock);
    }

    private void doSomeThing(InterProcessMutex lock) {
        CustomizeThreadPool.threadPool.execute(() -> {
            try {
                countDownLatch.await();
                zooKeeperLock.curatorLock(lock);
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zooKeeperLock.curatorReleaseLock(lock);
        });
        countDownLatch.countDown();
    }

}
