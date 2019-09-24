package com.xiamu.demo.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


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
        return zooKeeperClient.create(path, new byte[0], new ArrayList<>(), CreateMode.PERSISTENT);
    }

    /**
     * 创建一个临时顺序节点
     * @param path 路径(会加上顺序)
     * @param data 数据
     */
    public String createTemporaryNode(String path, String data) throws Exception {
        return zooKeeperClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * 删除一个节点
     */
    public void deleteNode(String path) throws Exception {
        zooKeeperClient.delete(path, -1);
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

}
