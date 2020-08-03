package com.ctgu.fmall.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticConfig.class);

/*    @Value("${spring.data.elasticsearch.hosts}")
    private String hosts;

    @Value("${spring.data.elasticsearch.port}")
    private int port;

    @Value("${spring.data.elasticsearch.scheme}")
    private String scheme;

    @Value("${spring.data.elasticsearch.timeout}")
    private int timeout;*/

    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        // 可以传httpHost数组
        logger.info("elasticsearch初始化配置开始");
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200,"http"));
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            // 设置超时
            return requestConfigBuilder.setSocketTimeout(90000);
        });
        logger.info("elasticsearch初始化配置完成");
        return new RestHighLevelClient(builder);
    }
}
