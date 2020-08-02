package com.ctgu.fmall.service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: yanghao
 * @Date: 2020/8/2 15:01
 * @PackageName:com.ctgu.fmall.service
 * @Description: TODO
 * @Version:V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SmsServiceTest extends TestCase {

    @Autowired
    SmsService smsService;

    @Test
    public void test(){
        log.info("发送结果："+smsService.send("12121221"));
    }
}