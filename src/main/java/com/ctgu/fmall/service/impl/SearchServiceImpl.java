package com.ctgu.fmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.config.ElasticSearchConfig;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.entity.ProductImage;
import com.ctgu.fmall.service.ProductImageService;
import com.ctgu.fmall.service.SearchService;
import com.ctgu.fmall.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: yanghao
 * @Date: 2020/8/3 18:46
 * @PackageName:com.ctgu.fmall.service.impl
 * @Description: TODO
 * @Version:V1.0
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource(name = "highLevelClient")
    private RestHighLevelClient client;

    @Autowired
    ProductImageService productImageService;

    public List<ProductVO> searchProduct(String keyword, int pageNo, int pageSize, int cid) throws IOException {
        log.info("Service搜索词：" + keyword);
        List<ProductVO> productList = new ArrayList<>();
//        AnalyzeRequest analyzeRequest = new AnalyzeRequest(ElasticSearchConfig.INDEX_NAME,keyword,"ik_smart");
//        analyzeRequest.text(keyword);
//        analyzeRequest.analyzer("ik_smart");       // 设置使用什么分词器  也可以使用 ik_max_word 它是细粒度分词
/*        Request request = new Request("GET", "_analyze");
        JSONObject entity = new JSONObject();
        entity.put("analyzer", "ik_max_word");
        //entity.put("analyzer", "ik_smart");
        entity.put("text", text);*/
//        request.setJsonEntity(entity.toJSONString());
//        Response response = this.client.getLowLevelClient().performRequest(request);

        //1.构建检索条件
        SearchRequest searchRequest = new SearchRequest();
        //2.指定要检索的索引库
        searchRequest.indices(ElasticSearchConfig.INDEX_NAME);
        //3.指定检索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //多条件查询
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(keyword, "name", "store");
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();        //query.bool
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("cid", cid);

        //检索指定分类下面的商品
        boolQueryBuilder.must(termQueryBuilder);
        boolQueryBuilder.must(multiMatchQuery);

        sourceBuilder.query(boolQueryBuilder);

        //排序规则
        sourceBuilder.sort("saleNum", SortOrder.DESC);
        sourceBuilder.sort("collectNum",SortOrder.DESC);
        sourceBuilder.sort("createTime",SortOrder.DESC);

        //4.结果高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(true); //如果该属性中有多个关键字 则都高亮
        highlightBuilder.field("name");
        highlightBuilder.preTags("<span style='color:#dd4b39'>");
        highlightBuilder.postTags("</span>");

        sourceBuilder.highlighter(highlightBuilder);
        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            //如果不做高亮，则可以直接转为json，然后转为对象
//            String value = hit.getSourceAsString();
//            ESProductTO esProductTO = JSON.parseObject(value, ESProductTO.class);
            //解析高亮字段
            //获取当前命中的对象的高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get("name");
            String newName = "";
            if (name != null) {
                //获取该高亮字段的高亮信息
                Text[] fragments = name.getFragments();
                //将前缀、关键词、后缀进行拼接
                for (Text fragment : fragments) {
                    newName += fragment;
                }
            }
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //将高亮后的值替换掉旧值
            sourceAsMap.put("name", newName);
            String json = JSON.toJSONString(sourceAsMap);
            log.info("转化之前：" + json);
            Product product = JSON.parseObject(json, Product.class);
            QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();
            imageQueryWrapper.eq("pid", product.getId());
            List<ProductImage> productImages = productImageService.list(imageQueryWrapper);
            ProductVO productVO = new ProductVO(product, productImages.get(0).getImgUrl());
            productList.add(productVO);
        }
        return productList;
    }
}
