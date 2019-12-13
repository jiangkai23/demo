package com.xiamu.demo.controller;

import com.xiamu.demo.redis.RedisDemo;
import com.xiamu.demo.redis.RedissonLocker;
import com.xiamu.demo.zookeeper.CuratorDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author XiaMu
 * @date 2019-08-02
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Resource
    private RedissonLocker redissonLocker;

    @Resource
    private RedisDemo redisDemo;

    @Resource
    private CuratorDemo curatorDemo;

    @GetMapping("/test")
    public void test() throws Exception {
//        redisDemo.test();
        redisDemo.testBloomFilter();
//        curatorDemo.test2();
    }
}
