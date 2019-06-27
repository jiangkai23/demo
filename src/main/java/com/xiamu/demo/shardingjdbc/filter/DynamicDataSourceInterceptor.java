package com.xiamu.demo.shardingjdbc.filter;

import com.xiamu.demo.shardingjdbc.datasource.DynamicDataSourceContextHolder;
import com.xiamu.demo.shardingjdbc.enums.DataSourceKey;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XiaMu
 * 自定义拦截器,用于切换数据源
 */
public class DynamicDataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String dataSourceKey = request.getParameter("");
        if ("".equals(dataSourceKey)) {
            DynamicDataSourceContextHolder.setDataSource(DataSourceKey.DATA_SOURCE_ONE);
        } else {
            DynamicDataSourceContextHolder.setDataSource(DataSourceKey.DATA_SOURCE_TWO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        DynamicDataSourceContextHolder.clearDataSource();
    }
}
