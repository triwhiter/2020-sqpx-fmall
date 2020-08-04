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
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
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
import java.util.HashMap;
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

    public HashMap searchProduct(String keyword, int pageNo, int pageSize, int cid) throws IOException {
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
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest=null;
//        无条件分页查询所有分类
        if(keyword.equals("null") && cid==0){
            searchRequest = new SearchRequest(ElasticSearchConfig.INDEX_NAME);
        }
        //查询指定分类的商品
        else if(cid!=0 && keyword.equals("null")){
            //1.构建检索条件
            searchRequest = new SearchRequest();
            //2.指定要检索的索引库
            searchRequest.indices(ElasticSearchConfig.INDEX_NAME);
            //3.指定检索条件
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("cid", cid);
            sourceBuilder.query(termQueryBuilder);
        }
        else if(cid==0 && !keyword.equals("null") ){
            //1.构建检索条件
            searchRequest = new SearchRequest();
            //2.指定要检索的索引库
            searchRequest.indices(ElasticSearchConfig.INDEX_NAME);
            //3.指定检索条件
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            QueryBuilders.matchQuery("keyword", keyword).boost(2f);
            //多条件查询
            MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(keyword, "name", "store");
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();        //query.bool
            //检索所有分类下面的商品
            boolQueryBuilder.must(multiMatchQuery);
            // 分词查询，boost 设置权重
            boolQueryBuilder.should(QueryBuilders.matchQuery("keyword", keyword).boost(2f));
            //拼音查询
            boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("keyword.pinyin", keyword).boost(2f));

            sourceBuilder.query(boolQueryBuilder);

            //4.结果高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.requireFieldMatch(true); //如果该属性中有多个关键字 则都高亮
            highlightBuilder.field("name");
            highlightBuilder.preTags("<span style='color:#dd4b39'>");
            highlightBuilder.postTags("</span>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        //检索指定分类下的商品
        else {
            //1.构建检索条件
            searchRequest = new SearchRequest();
            //2.指定要检索的索引库
            searchRequest.indices(ElasticSearchConfig.INDEX_NAME);
            //3.指定检索条件
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            QueryBuilders.matchQuery("keyword", keyword).boost(2f);
            //多条件查询
            MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(keyword, "name", "store");
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();        //query.bool
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("cid", cid);

            //检索指定分类下面的商品
            boolQueryBuilder.must(termQueryBuilder);
            boolQueryBuilder.must(multiMatchQuery);
            // 分词查询，boost 设置权重
            boolQueryBuilder.should(QueryBuilders.matchQuery("keyword", keyword).boost(2f));
            //拼音查询
            boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("keyword.pinyin", keyword).boost(2f));

            sourceBuilder.query(boolQueryBuilder);

            //4.结果高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.requireFieldMatch(true); //如果该属性中有多个关键字 则都高亮
            highlightBuilder.field("name");
            highlightBuilder.preTags("<span style='color:#dd4b39'>");
            highlightBuilder.postTags("</span>");

            sourceBuilder.highlighter(highlightBuilder);
        }
        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        //排序规则
     sourceBuilder.sort("saleNum", SortOrder.DESC);
        sourceBuilder.sort("collectNum",SortOrder.DESC);
        sourceBuilder.sort("id", SortOrder.ASC);
        sourceBuilder.sort("createTime",SortOrder.DESC);

        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            //如果不做高亮，则可以直接转为json，然后转为对象
//            String value = hit.getSourceAsString();
//            ESProductTO esProductTO = JSON.parseObject(value, ESProductTO.class);
            //解析高亮字段
            //获取当前命中的对象的高亮的字段
            if(!keyword.equals("null")){
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
                    //将高亮后的值替换掉旧值
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    sourceAsMap.put("name", newName);
                }

            }
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String json = JSON.toJSONString(sourceAsMap);
            Product product = JSON.parseObject(json, Product.class);
            QueryWrapper<ProductImage> imageQueryWrapper = new QueryWrapper<>();

            imageQueryWrapper.eq("pid", product.getId());

            List<ProductImage> productImages = productImageService.list(imageQueryWrapper);
            ProductVO productVO = new ProductVO(product, productImages.get(0).getImgUrl());
            productList.add(productVO);
        }
        HashMap hashMap = new HashMap<>();
        hashMap.put("list",productList);
        hashMap.put("total", response.getHits().getTotalHits().value);
        return hashMap;
    }
}
