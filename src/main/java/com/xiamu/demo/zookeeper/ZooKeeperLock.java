package com.xiamu.demo.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeDeleted;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;


/**
 * @author XiaMu
 * @date 2019-09-23
 */
@Slf4j
@Component
public class ZooKeeperLock {

    @Resource
    private ZooKeeper zooKeeperClient;


    /**
     * 创建一个path路径
     */
    public String createPath(String path) throws Exception {
        return zooKeeperClient.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 创建一个临时节点
     *
     * @param path 路径(会加上顺序)
     * @param data 数据
     */
    public String createTemporaryNode(String path, String data) throws Exception {
        return zooKeeperClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public void setData(String path, String data) throws KeeperException, InterruptedException {
        zooKeeperClient.setData(path, data.getBytes(), -1);
    }

    /**
     * 删除一个节点
     */
    public void deleteNode(String path) throws Exception {
        zooKeeperClient.delete(path, -1);
        log.info("{}释放锁成功", Thread.currentThread().getName());
    }

    /**
     * 注册一个监听器
     */
    public void registerWatch(String path) throws Exception {
        List<String> children1 = zooKeeperClient.getChildren(path, event -> {
            System.out.println("监听");
        }, null);
        System.out.println(children1.toString());
        byte[] data1 = zooKeeperClient.getData(path, true, null);
        List<String> children = zooKeeperClient.getChildren(path, true);
        System.out.println(children.toString());
    }

    public void lock(String path) throws Exception {
        boolean hasLock = false;
        while (!hasLock) {
            try {
                this.createTemporaryNode(path, "data");
                hasLock = true;
                log.info("{}获取锁成功", Thread.currentThread().getName());
            } catch (Exception e) {
                synchronized (this) {
                    try {
                        zooKeeperClient.getData(path, event -> {
                            System.out.println("jingting");
                            if (SyncConnected.equals(event.getState()) && NodeDeleted.equals(event.getType())) {
                                notifyWait();
                            }
                        }, null);
                        wait();
                    } catch (KeeperException.NoNodeException ex) {
                        log.info("节点已不存在");
                    }
                }
            }
        }
    }

    public synchronized void notifyWait() {
        notifyAll();
    }

}
