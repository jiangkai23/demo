package com.xiamu.demo.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author XiaMu
 * @date 2019-08-02
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson() throws IOException {
        // 读取配置文件创建客户端
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("application-redisson.yml"));
        return Redisson.create(config);
    }

}
