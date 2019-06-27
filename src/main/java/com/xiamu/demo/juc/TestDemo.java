package com.xiamu.demo.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 */
public class TestDemo {
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new WaitRunner(), "等待线程");
        Thread notifyThread = new Thread(new NotifyRunner(), "通知线程");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        notifyThread.start();
        System.out.println("主线程结束");
    }

    static class WaitRunner implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("要开始等待了");
                    lock.wait();
                    System.out.println("我收到通知了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class NotifyRunner implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("通知类来了");
                    TimeUnit.SECONDS.sleep(10);
                    lock.notify();

                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("通知发出了");

            }
        }
    }

}
