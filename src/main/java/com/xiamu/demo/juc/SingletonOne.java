package com.xiamu.demo.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author XiaMu
 * 懒汉模式单例,线程不安全
 */
public class SingletonOne {

    private SingletonOne() {}

    private static SingletonOne singletonOne = null;

    public static SingletonOne getInstance() {
        if (null == singletonOne) {
            singletonOne = new SingletonOne();
        }
        return singletonOne;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = CustomizeThreadPool.threadPool;
        for (int i = 0; i < 100; i++) {
            threadPool.execute(() -> {
                    SingletonOne instance = getInstance();
                    System.out.println(instance.hashCode());
            });
        }
        threadPool.shutdown();
    }
}
