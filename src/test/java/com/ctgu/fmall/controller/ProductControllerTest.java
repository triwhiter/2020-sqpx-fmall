package com.ctgu.fmall.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.config.ElasticSearchConfig;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.entity.ProductImage;
import com.ctgu.fmall.service.ProductImageService;
import com.ctgu.fmall.service.ProductService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Random;

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
     * 初始化ES的索引，默认使用Standard分词器
     * @throws IOException
     */
    @Test
    public void initEsIndex() throws IOException {
        List<Product>productList=productService.list(null);
        if(productList.size()!=0){
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout("120s");
            for(Product p:productList){
                log.info(p.toString());
                QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
                imageQueryWrapper.eq("pid",p.getId());
                List<ProductImage> productImages=productImageService.list(imageQueryWrapper);
                if(productImages.size()>0){
                    IndexRequest indexRequest=new IndexRequest(ElasticSearchConfig.INDEX_NAME).source(JSON.toJSONString(p), XContentType.JSON);
                    indexRequest.id(String.valueOf(p.getId()));
//                    indexRequest.setIfPrimaryTerm(1);
                    bulkRequest.add(indexRequest);
                }
            }
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("索引是否初始化成功："+!bulk.hasFailures());
        }
    }


    /**
     * 全表给商品添加固定前缀
     */
    @Test
    public void resizeID(){
       List<ProductImage> products=productImageService.list(null);
       products.forEach(p->{
               p.setImgUrl("http://img14.360buyimg.com/n1/"+p.getImgUrl());
               productImageService.updateById(p);
       });
    }


}