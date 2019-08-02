package com.xiamu.demo.shardingjdbc.datasource;

/*import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.xiamu.demo.shardingjdbc.enums.DataSourceKey;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;*/

/**
 * @author XiaMu
 * 动态数据源配置
 */
//@Configuration
public class DynamicDataSourceConfiguration {

    /*@Resource
    private Environment env;

    @Bean(name = "dataOne")
    public DataSource dbRunning() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.running.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.running.url"));
        props.put("username", env.getProperty("spring.datasource.running.username"));
        props.put("password", env.getProperty("spring.datasource.running.password"));
        props.put("initConnectionSqls", "set names utf8mb4");
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean(name = "dataTwo")
    public DataSource dbPlay() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.play.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.play.url"));
        props.put("username", env.getProperty("spring.datasource.play.username"));
        props.put("password", env.getProperty("spring.datasource.play.password"));
        props.put("initConnectionSqls", "set names utf8mb4");
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(@Qualifier("dataOne") DataSource dataOne, @Qualifier("dataTwo") DataSource dataTwo) {
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(dataOne);
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceKey.DATA_SOURCE_ONE, dataOne);
        dataSourceMap.put(DataSourceKey.DATA_SOURCE_TWO, dataTwo);
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.xiamu.demo.shardingjdbc.entity");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }*/
}
