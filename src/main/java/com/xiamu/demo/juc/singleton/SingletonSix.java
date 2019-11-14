package com.xiamu.demo.juc.singleton;

/**
 * @author XiaMu
 * 枚举类的单例模式,由JVM保证线程安全
 * 值得注意的是这种单例可以避免通过反射和序列化反序列化生成不同对象!
 */
public enum  SingletonSix {

    /** 实例*/
    INSTANCE;

    public SingletonSix getInstance() {
        return INSTANCE;
    }
}
