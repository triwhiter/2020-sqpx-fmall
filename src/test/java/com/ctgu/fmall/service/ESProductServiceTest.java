package com.ctgu.fmall.service;

import com.ctgu.fmall.vo.ProductVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: yanghao
 * @Date: 2020/8/3 14:43
 * @PackageName:com.ctgu.fmall.service
 * @Description: TODO
 * @Version:V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ESProductServiceTest extends TestCase {
    @Autowired
    private ESProductService esProductService;

    @Test
    public void testSearchHighLight() throws IOException {
        List<ProductVO> products = esProductService.searchProduct("面");
        for (ProductVO  product : products) {
            log.info(product.toString());
        }}
}