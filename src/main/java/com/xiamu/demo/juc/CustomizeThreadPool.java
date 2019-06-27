package com.xiamu.demo.juc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * 自定义线程池
 */
public class CustomizeThreadPool {

    private static UserThreadFactory factory = new UserThreadFactory("demoThread");

    public static ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(5, 20, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(50), factory, new ThreadPoolExecutor.AbortPolicy());
}
