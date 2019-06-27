package com.xiamu.demo.shardingjdbc.datasource;


import com.xiamu.demo.shardingjdbc.enums.DataSourceKey;

/**
 * @author XiaMu
 * 用于保存当前线程使用数据源
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<DataSourceKey> CURRENT_DATASOURCE = new ThreadLocal<>();

    public static void clearDataSource() {
        CURRENT_DATASOURCE.remove();
    }

    public static DataSourceKey getDataSource() {
        return CURRENT_DATASOURCE.get();
    }

    public static void setDataSource(DataSourceKey value) {
        CURRENT_DATASOURCE.set(value);
    }

}
