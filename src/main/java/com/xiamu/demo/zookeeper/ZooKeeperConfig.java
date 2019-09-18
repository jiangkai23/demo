package com.xiamu.demo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author XiaMu
 * 2019-09-18
 */
@Configuration
public class ZooKeeperConfig {

    @PostConstruct
    public void init() {
        zooKeeperClient().start();
    }

    @Bean
    public CuratorFramework zooKeeperClient() {
        return CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(10 * 1000)
                .sessionTimeoutMs(60 * 1000)
                .namespace("xiamu")
                .build();
    }


}
