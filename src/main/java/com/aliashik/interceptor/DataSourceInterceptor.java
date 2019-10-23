package com.aliashik.interceptor;

import com.aliashik.config.DataSourceType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String contextPath = request.getServletContext().getContextPath();
        String prefixPublisher = contextPath + "/bank1";
        String prefixAdvertiser = contextPath + "/bank2";


        String uri = request.getRequestURI();
        System.out.println("URI:" + uri);

        if (uri.startsWith(prefixPublisher)) {
            request.setAttribute("database", DataSourceType.DB1);
        } else if (uri.startsWith(prefixAdvertiser)) {
            request.setAttribute("database", DataSourceType.DB2);
        }
        return true;
    }


}