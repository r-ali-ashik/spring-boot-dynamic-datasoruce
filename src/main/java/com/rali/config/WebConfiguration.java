package com.rali.config;

import com.rali.interceptor.DataSourceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final DataSourceInterceptor dataSourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataSourceInterceptor);
    }
}
