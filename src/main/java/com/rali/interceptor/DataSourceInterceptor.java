package com.rali.interceptor;

import com.rali.constant.DataSourceType;
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
        String prefixBank1 = contextPath + "/bank1";
        String prefixBank2 = contextPath + "/bank2";


        String uri = request.getRequestURI();
        System.out.println("URI:" + uri);

        if (uri.startsWith(prefixBank1)) {
            request.setAttribute("database", DataSourceType.DB1);
        } else if (uri.startsWith(prefixBank2)) {
            request.setAttribute("database", DataSourceType.DB2);
        }
        return true;
    }
}