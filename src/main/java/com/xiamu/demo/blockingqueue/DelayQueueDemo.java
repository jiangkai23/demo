package com.xiamu.demo.blockingqueue;

import com.xiamu.demo.juc.threadpool.CustomizeThreadPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaMu
 * @date 2019-07-26
 */
@Slf4j
public class DelayQueueDemo {

    /**
     * 任务队列
     */
    private static DelayQueue<Task> taskQueue = new DelayQueue<>();


    public static void main(String[] args) throws InterruptedException {

        // 消费者
        CustomizeThreadPool.threadPool.execute(() -> {
            Task task = null;
            try {
                task = taskQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != task) {
                long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                log.info("当前时间:{},{}的默认评论是{};没有评论则插入", currentTime, task.getUuid(), task.getRemark());
            }
        });

        TimeUnit.SECONDS.sleep(3);

        // 生产者
        CustomizeThreadPool.threadPool.execute(() -> {
            Task task = new Task();
            // 设置任务详情包括延时时间
            long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            task.init("uuid", "remake", currentTime + 10);
            taskQueue.put(task);
            log.info("{}添加{}默认评论任务成功!", currentTime, task.getUuid());
        });


    }

    @Data
    static class Task implements Delayed {

        /**
         * 操作记录的uuid
         */
        private String uuid;

        /**
         * 评论内容
         */
        private String remark;

        /**
         * 该元素需要消费的时间
         */
        private Long executeTime;

        /**
         * 初始化任务
         * @param uuid uuid
         * @param remark 评论
         * @param executeTime 执行时间
         */
        public void init(String uuid, String remark, Long executeTime) {
            this.uuid = uuid;
            this.remark = remark;
            this.executeTime = executeTime;
        }


        /**
         * 计算延时时间
         * @param unit 计算单位
         * @return 执行时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            return unit.convert(executeTime - currentTime, TimeUnit.SECONDS);
        }

        /**
         * 延时时间比较,用于延时阻塞队列的元素排序
         * @param o 比较元素
         * @return 比较结果
         */
        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            }
            return 0;
        }

    }
}
