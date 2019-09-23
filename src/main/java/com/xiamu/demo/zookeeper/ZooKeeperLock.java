package com.xiamu.demo.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.file.Path;

/**
 * @author XiaMu
 * @date 2019-09-23
 */
@Slf4j
@Component
public class ZooKeeperLock {

    @Resource
    private CuratorFramework zooKeeperClient;


    /**
     * curator获取锁
     */
    public InterProcessMutex getCuratorLock(String path) {
        return new InterProcessMutex(zooKeeperClient, path);
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

    public void createPath() throws Exception {
        String path = zooKeeperClient.create().forPath("path");
    }

    public void createTemporaryNode(String path) throws Exception {
        String s = zooKeeperClient.create().withMode(CreateMode.EPHEMERAL).forPath(path);
    }
}
