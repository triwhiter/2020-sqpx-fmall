package com.ctgu.fmall.service;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Auther: yanghao
 * @Date: 2020/8/3 19:14
 * @PackageName:com.ctgu.fmall.service
 * @Description: TODO
 * @Version:V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SearchServiceTest extends TestCase {
    @Autowired
    SearchService searchService;

    @Test
    public void search() throws IOException {
        System.out.println(searchService.searchProduct("mianbao",1,100,1));

//        System.out.println(searchService.searchProduct("许振爱吃面包坚果测试",1,100,1));
    }

}