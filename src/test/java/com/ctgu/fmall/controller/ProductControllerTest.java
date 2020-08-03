package com.ctgu.fmall.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.fmall.config.ElasticSearchConfig;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.entity.ProductImage;
import com.ctgu.fmall.service.ProductImageService;
import com.ctgu.fmall.service.ProductService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.ProductVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: yanghao
 * @Date: 2020/8/3 21:41
 * @PackageName:com.ctgu.fmall.controller
 * @Description: TODO
 * @Version:V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductControllerTest extends TestCase {
    @Autowired
    ProductService productService;

    @Resource(name = "highLevelClient")
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    ProductImageService productImageService;

    /**
     * 初始化ES的索引
     * @throws IOException
     */
    @Test
    public void initEsIndex() throws IOException {
        List<Product>productList=productService.list(null);
        if(productList.size()!=0){
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout("60s");
            for(Product p:productList){
                log.info(p.toString());
                QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
                imageQueryWrapper.eq("pid",p.getId());
                List<ProductImage> productImages=productImageService.list(imageQueryWrapper);
                if(productImages.size()>0){
                    IndexRequest indexRequest=new IndexRequest(ElasticSearchConfig.INDEX_NAME).source(JSON.toJSONString(p), XContentType.JSON);
                    indexRequest.setIfPrimaryTerm(1);
                    bulkRequest.add(indexRequest);
                }
            }
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("索引是否初始化成功："+!bulk.hasFailures());
        }
    }

}