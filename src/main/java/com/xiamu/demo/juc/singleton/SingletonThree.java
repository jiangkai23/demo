package com.xiamu.demo.juc.singleton;

/**
 * @author XiaMu
 * 线程安全的懒汉模式单例
 */
public class SingletonThree {

    private SingletonThree() {}

    private SingletonThree singletonThree = null;

    public synchronized SingletonThree getInstance() {
        if (null == singletonThree) {
            singletonThree = new SingletonThree();
        }
        return singletonThree;
    }
}
