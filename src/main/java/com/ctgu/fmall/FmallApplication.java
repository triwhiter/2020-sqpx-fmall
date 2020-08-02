package com.ctgu.fmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//手动加载自定义配置文件
@PropertySource(value = {
        "classpath:config.properties",
}, encoding = "utf-8",ignoreResourceNotFound = true)
@SpringBootApplication
@MapperScan("com.ctgu.fmall.mapper")
public class FmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmallApplication.class, args);
        System.out.println("http://localhost:8088/api/swagger-ui.html");
    }

}
