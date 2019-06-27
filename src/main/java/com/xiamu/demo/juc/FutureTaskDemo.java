package com.xiamu.demo.juc;

import java.util.concurrent.*;

/**
 * @author XiaMu
 */
public class FutureTaskDemo {

    static Integer count = 0;

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(500);

    static CountDownLatch countDownLatch = new CountDownLatch(500);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int sum = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(500);
        for (int i = 0; i < 500; i++) {
            int number = i;
            Future<Integer> result = executorService.submit(() -> {
                System.out.println("线程" + number);
                cyclicBarrier.await();
                System.out.println("线程" + number + "执行完毕" + count);
                countDownLatch.countDown();
                return count++;
            });
//            sum = result.get();
        }
        countDownLatch.await();
        System.out.println("执行结果：" + sum);
        System.out.println("执行结果：" + count);
    }



}
