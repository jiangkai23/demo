package com.xiamu.demo.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * @date 2019-07-25
 */
@Slf4j
public class ZookeeperDemo {
    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(10 * 1000)
                .sessionTimeoutMs(60 * 1000)
                .namespace("xiamu")
                .build();
    }

    public static void main(String[] args) throws Exception {
        String path = "/demo";
        byte[] data = "demoData".getBytes();
        CuratorFramework zooKeeperClient = getClient();
        zooKeeperClient.start();
        log.info("{}", zooKeeperClient.getState());
        String s = zooKeeperClient.create().forPath(path, data);
        log.info("{}", s);
    }
}
