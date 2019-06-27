package com.xiamu.demo.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author XiaMu
 */
public class LockDemo {
    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new UserRunnable(), "A线程");
        a.start();
        countDownLatch.countDown();
        Thread b = new Thread(new UserRunnable(), "B线程");
        b.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("countDownLatch倒计时结束时间" + System.currentTimeMillis());
        countDownLatch.countDown();

        /*Thread a = new Thread(new LockRunnable(), "A获取锁线程");
        Thread b = new Thread(new LockRunnable(), "B获取锁线程");
        a.start();
        countDownLatch.countDown();
        b.start();
        TimeUnit.SECONDS.sleep(1);
        countDownLatch.countDown();*/
    }

    static class UserRunnable implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println( threadName + "启动时间" + System.currentTimeMillis());
            try {
                countDownLatch.await();
                System.out.println(threadName + "重新执行时间" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock();
            try {
                System.out.println(threadName + "拿到了锁" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(2);
                System.out.println("waitQueueLength:" + lock.getWaitQueueLength(condition));
                System.out.println("queueLength:" + lock.getQueueLength());

                if ("A线程".equals(threadName)) {
                    System.out.println(threadName + "需要等待");
                    condition.await();
                }
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(threadName + "释放了锁" + System.currentTimeMillis());
            }

        }
    }

    static class LockRunnable implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            lock.lock();
            try {
                System.out.println(threadName + "拿到了锁" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(20);
                System.out.println("queueLength:" + lock.getQueueLength());
                System.out.println("waitQueueLength:" + lock.getWaitQueueLength(condition));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(threadName + "释放了锁" + System.currentTimeMillis());
            }

        }
    }

}
