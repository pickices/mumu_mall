package com.liuxinchi.mumu_mall.config;

import com.liuxinchi.mumu_mall.common.Constant;
import com.liuxinchi.mumu_mall.interceptor.adminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 拾荒老冰棍
 */
@Configuration
public class MumuMallWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    adminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+ Constant.FILE_UPLOAD_DIR);
    }
}
