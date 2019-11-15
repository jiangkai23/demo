package com.xiamu.demo.zookeeper;

import com.xiamu.demo.juc.threadpool.CustomizeThreadPool;
import lombok.extern.slf4j.Slf4j;
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
public class CuratorDemo {

    @Resource
    private CuratorLock curatorLock;

    @Resource
    private ZooKeeperLock zooKeeperLock;

    private CountDownLatch countDownLatch = new CountDownLatch(2);

    public void test() {
        String path = "/lock";
        InterProcessMutex lock = curatorLock.getCuratorLock(path);
        this.doSomeThing(lock);
        this.doSomeThing(lock);
    }

    public void test2() throws Exception {
        String path = "/xiamu/lock/lock-key";
        doSomeThing2(path);
        doSomeThing2(path);
    }

    private void doSomeThing(InterProcessMutex lock) {
        CustomizeThreadPool.threadPool.execute(() -> {
            try {
                countDownLatch.await();
                curatorLock.curatorLock(lock);
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            curatorLock.curatorReleaseLock(lock);
        });
        countDownLatch.countDown();
    }

    private void doSomeThing2(String path) {
        CustomizeThreadPool.threadPool.execute(() -> {
            try {
                countDownLatch.await();
                zooKeeperLock.lock(path);
                TimeUnit.SECONDS.sleep(2);
                zooKeeperLock.setData(path, "aa");
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                zooKeeperLock.deleteNode(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        countDownLatch.countDown();
    }

}
