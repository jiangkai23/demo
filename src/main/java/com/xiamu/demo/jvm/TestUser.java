package com.xiamu.demo.jvm;

/**
 * @author XiaMu
 * @date 2019-10-15
 * JVM字节码相关博客链接：https://blog.csdn.net/seesun2012/article/details/84729598
 */
public class TestUser extends FatherUser{

    public User initUser(int age, String name){
        User user = new User(age, name);
        return user;
    }

}
