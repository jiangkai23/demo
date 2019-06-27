package com.xiamu.demo.juc;

/**
 * @author XiaMu
 * 饿汉模式单例,线程安全
 */
public class SingletonTwo {

    private SingletonTwo() {}

    private SingletonTwo singletonTwo = new SingletonTwo();

    public SingletonTwo getInstance() {
        return singletonTwo;
    }
}
