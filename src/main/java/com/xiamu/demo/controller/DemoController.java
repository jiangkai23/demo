package com.xiamu.demo.controller;

import com.xiamu.demo.redisson.RedissonLocker;
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

    @GetMapping("/test")
    public void test() {

    }
}
