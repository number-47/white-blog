package com.number47.white.blog.config;

import com.number47.white.blog.filter.ExceptionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: white-blog
 * @description:
 * @author: number47
 * @create: 2020-08-12 14:08
 **/
@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Value("${img.path}")
    private String imgPath;

    @Autowired
    private ExceptionFilter exceptionFilter;

    @Bean
    public FilterRegistrationBean exceptionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(exceptionFilter);
        registration.setName("exceptionFilter");
        //此处尽量小，要比其他Filter靠前
        registration.setOrder(-1);
        return registration;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在intercept中再配置header
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POSt","GET","PUT","OPTION","DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + imgPath);
    }



}
