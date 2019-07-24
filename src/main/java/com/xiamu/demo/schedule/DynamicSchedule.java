package com.xiamu.demo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author XiaMu
 */
@Component
@Slf4j
public class DynamicSchedule {

    /**
     * 维护各个定时任务的future
     */
    private Map<String, ScheduledFuture<?>> futureMap = new HashMap<>();

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 实例化一个线程池任务调度类,可以使用自定义的ThreadPoolTaskScheduler
     * (默认大小为1会导致定时任务阻塞运行,可通过setPollSize()设置)
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 添加定时任务
     */
    public void add(String name, String cron) {
        // 从数据库动态获取执行周期cron
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(new SendMsg(name), new CronTrigger(cron));
        futureMap.put(name, future);
        log.info("{}任务添加成功！！！{}", name, LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
    }

    /**
     * 停止定时任务
     */
    public void remove(String name) {
        ScheduledFuture<?> future = futureMap.get(name);
        if (future != null) {
            boolean cancel = future.cancel(true);
            if (cancel) {
                log.info("{}任务停止成功！！！{}", name, LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
            } else {
                log.info("任务停止失败！！！");
            }
        } else {
            log.info("任务已经停止！！！");
        }
    }

    /**
     * 具体任务
     */
    class SendMsg implements Runnable {
        private String name;
        SendMsg(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            log.info("{}发送消息{}", name, LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        }
    }

}
