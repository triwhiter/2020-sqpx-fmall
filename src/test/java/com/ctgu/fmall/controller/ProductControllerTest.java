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
/*

    @Test
    @Transactional
    public void resizeID(){
       List<Product> products=productService.list(null);
       products.forEach(p->{
           if(p.getId()>1147483647){
               log.info(p.toString());
               long smallPid=p.getId();
               QueryWrapper<ProductImage> wrapper = new QueryWrapper<>();
               wrapper.eq("pid",p.getId());
               List<ProductImage> productImages=productImageService.list(wrapper);
               log.info(productImages.toString());
               while (smallPid>1147483647){
                   Random rand = new Random();
                   smallPid=smallPid/ (rand.nextInt(20)+20);
                   log.info("缩小ID"+String.valueOf(smallPid));
               }
           }
       });
    }
*/

}