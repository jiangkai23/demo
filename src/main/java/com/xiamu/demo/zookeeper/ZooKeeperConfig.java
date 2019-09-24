package com.xiamu.demo.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author XiaMu
 * 2019-09-18
 */
@Configuration
@Slf4j
public class ZooKeeperConfig {

    @PostConstruct
    public void init() {
        curatorClient().start();
    }

    @Bean
    public CuratorFramework curatorClient() {
        return CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(10 * 1000)
                .sessionTimeoutMs(60 * 1000)
                .namespace("xiamu")
                .build();
    }

    @Bean
    public ZooKeeper zooKeeperClient() throws IOException, InterruptedException {
        CountDownLatch countDown=new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, watchedEvent -> {
            //获取事件的状态
            Watcher.Event.KeeperState state = watchedEvent.getState();
            //获取事件的类型
            Watcher.Event.EventType type = watchedEvent.getType();
            if(Watcher.Event.KeeperState.SyncConnected.equals(state) && Watcher.Event.EventType.None.equals(type)){
                //连接建立成功，则释放信号量，让阻塞的程序继续向下执行
                countDown.countDown();
                log.info("zk建立连接成功");
            }
        });
        countDown.await();
        return zooKeeper;
    }


}
