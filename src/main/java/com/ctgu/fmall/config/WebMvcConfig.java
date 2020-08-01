package com.ctgu.fmall.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: yanghao
 * @Date: 2020/8/1 13:17
 * @PackageName:com.ctgu.fmall.config
 * @Description: TODO
 * @Version:V1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    //配置跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }

}