package com.xiamu.demo.shardingjdbc.aspect;


import com.xiamu.demo.shardingjdbc.annotation.DataSource;
import com.xiamu.demo.shardingjdbc.datasource.DynamicDataSourceContextHolder;
import com.xiamu.demo.shardingjdbc.enums.DataSourceKey;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author XiaMu
 * 根据注解动态切换数据源切面
 */
@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(dataSource)")
    public void dataSourceBefore(JoinPoint point, DataSource dataSource) {
        String key = dataSource.key();
        if (DataSourceKey.DATA_SOURCE_ONE.toString().equals(key)) {
            DynamicDataSourceContextHolder.setDataSource(DataSourceKey.DATA_SOURCE_ONE);
        } else {
            DynamicDataSourceContextHolder.setDataSource(DataSourceKey.DATA_SOURCE_TWO);
        }
    }

    @After("@annotation(dataSource)")
    public void dataSourceAfter(JoinPoint point, DataSource dataSource) {
        DynamicDataSourceContextHolder.clearDataSource();
    }

}
