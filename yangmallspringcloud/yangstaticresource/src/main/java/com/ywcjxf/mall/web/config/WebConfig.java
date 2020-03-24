package com.ywcjxf.mall.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*.js","/**/*.css").addResourceLocations("file:/Users/eugeneyoung/Downloads/mall/").setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS));
        registry.addResourceHandler("/**/*.woff","/**/*.ttf").addResourceLocations("file:/Users/eugeneyoung/Downloads/mall/").setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS));
        registry.addResourceHandler("/**/*.html","/**/*.png","/**/*.PNG","/**/*.jpg","/**/*.JPG").addResourceLocations("file:/Users/eugeneyoung/Downloads/mall/","file:/Users/eugeneyoung/Downloads/item/","file:/Users/eugeneyoung/Downloads/itemimage/").setCacheControl(CacheControl.maxAge(3, TimeUnit.DAYS));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**/*.woff").allowedOrigins("*").allowedMethods("*");
        registry.addMapping("/**/*.ttf").allowedOrigins("*").allowedMethods("*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
