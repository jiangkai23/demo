package com.xiamu.demo.juc.singleton;

/**
 * @author XiaMu
 * 线程安全的双重锁检测单例,必须加上volatile才会线程安全。
 * 因为singletonFour = new SingletonFour()在jvm中分三步执行，
 * 1、分配对象的内存空间
 * 2、初始化对象
 * 3、将引用指向分配的内存空间
 * 由于CPU的指令重排序在多线程环境下不保证2、3两步的执行顺序,
 * 可能某个线程第一次判空时为false直接使用,但实际对象并未初始化完成
 * */
public class SingletonFour {

    private SingletonFour() {}

    private static volatile SingletonFour singletonFour = null;

    public SingletonFour getInstance() {
        if (null == singletonFour) {
            synchronized (SingletonFour.class) {
                if (null == singletonFour) {
                    singletonFour = new SingletonFour();
                }
            }
        }
        return singletonFour;
    }

}
