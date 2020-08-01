package com.ctgu.fmall;


import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FmallApplicationTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        log.warn(passwordEncoder.encode("123123"));
        log.warn(String.valueOf(passwordEncoder.matches("123123","$2a$10$BDUurvvxhAdeDtlRaoXqyeZAequCsXNDX1hvHfNdnw5kasdI7OQp.")));
    }

}
