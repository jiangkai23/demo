package com.xiamu.demo.juc.singleton;

/**
 * @author XiaMu
 * 静态内部类的懒汉单例模式,线程安全
 */
public class SingletonFive {

    private SingletonFive() {}

    private static class InnerSingleton {
        private static final SingletonFive SINGLETON_FIVE = new SingletonFive();
    }

    public SingletonFive getInstance() {
        return InnerSingleton.SINGLETON_FIVE;
    }
}