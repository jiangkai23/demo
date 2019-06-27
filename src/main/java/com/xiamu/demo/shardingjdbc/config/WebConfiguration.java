package com.xiamu.demo.shardingjdbc.config;

import com.xiamu.demo.shardingjdbc.filter.DynamicDataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author XiaMu
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器
        registry.addInterceptor(new DynamicDataSourceInterceptor())
                .addPathPatterns("/**");
    }

}
