package com.ctgu.fmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

//手动加载自定义配置文件
@PropertySource(value = {"classpath:config.properties"}, encoding = "UTF-8",ignoreResourceNotFound = true)
@SpringBootApplication
@EnableAsync
@MapperScan("com.ctgu.fmall.mapper")
public class FmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallApplication.class, args);
        System.out.println("http://localhost:8088/api/swagger-ui.html");
    }

}
