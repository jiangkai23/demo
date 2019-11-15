package com.xiamu.demo.juc.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XiaMu
 * 线程池工厂
 */
public class UserThreadFactory implements ThreadFactory {

    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger();

    UserThreadFactory(String group) {
        namePrefix = "UserThreadFactory-" + group + "-work";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = namePrefix + nextId.getAndIncrement();
        return new Thread(null, runnable, name, 0);
    }
}
