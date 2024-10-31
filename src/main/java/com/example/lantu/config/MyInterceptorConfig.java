package com.example.lantu.config;

import com.example.lantu.interceptor.jwtVailidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private jwtVailidateInterceptor jwtSignatureValidator;

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        InterceptorRegistration registration = registry.addInterceptor(jwtSignatureValidator);
        registration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/info",
                        "/user/logout"
                );
    }
}
