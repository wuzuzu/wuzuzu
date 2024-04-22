package com.sparta.wuzuzu.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ElasticsearchConfig {


    @Value("${spring.elasticsearch.rest.uris}")
    private String elasticsearchUrl;

    @Value("${spring.elasticsearch.rest.apiKey}")
    private String apiKey;

    private final String authorization = "Authorization";
    private final String key = "ApiKey ";

    @Bean
    public RestClient restClient() {
        return RestClient.builder(HttpHost.create(elasticsearchUrl))
            .setDefaultHeaders(new Header[]{new BasicHeader(authorization, key + apiKey)})
            .build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = restClient();
        ElasticsearchTransport transport = new RestClientTransport(restClient,
            new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

}




