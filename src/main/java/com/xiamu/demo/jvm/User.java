package com.xiamu.demo.jvm;

/**
 * @author XiaMu
 * @date 2019-10-15
 */
public class User extends FatherUser{

    private String name;

    private int age;

    public User() {}
    public User(int age, String name) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
