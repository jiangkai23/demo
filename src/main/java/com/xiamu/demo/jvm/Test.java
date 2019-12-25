package com.xiamu.demo.jvm;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        FatherUser user = UserTypeEnum.USER.getUser();
        Thread.sleep(300000);
    }
}
