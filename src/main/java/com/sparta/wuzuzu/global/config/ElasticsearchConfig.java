package com.sparta.wuzuzu.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//import org.springframework.data.elasticsearch.support.HttpHeaders;


@Configuration
public class ElasticsearchConfig {

//    @Value("${spring.elasticsearch.rest.uris}")
//    private String elasticsearchUrl;
//
//    @Value("${spring.elasticsearch.rest.username}")
//    private String username;
//
//    @Value("${spring.elasticsearch.rest.password}")
//    private String password;
//
//    @Value("${spring.elasticsearch.rest.apiKey}")
//    private String apiKey;
//
//    private final String authorization = "Authorization";
//
//    @Bean
//    public RestClient restClient() {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//            new UsernamePasswordCredentials(username, password));
//
//        RestClientBuilder builder = RestClient.builder(HttpHost.create(elasticsearchUrl))
//            .setHttpClientConfigCallback(httpClientBuilder ->
//                httpClientBuilder
//                    .setDefaultCredentialsProvider(credentialsProvider)
//            )
//            .setDefaultHeaders(new Header[] {new BasicHeader(authorization, "ApiKey " + apiKey)});
//
//        return builder.build();
//    }
//
//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        RestClient restClient = restClient();
//        JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper();
//        ElasticsearchTransport transport = new RestClientTransport(restClient, jsonpMapper);
//        return new ElasticsearchClient(transport);
//    }

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
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

//    @Value("${spring.elasticsearch.rest.uris}")
//    private String elasticsearchUrl;
//
//    @Value("${spring.elasticsearch.rest.username}")
//    private String username;
//
//    @Value("${spring.elasticsearch.rest.password}")
//    private String password;
//
//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//            new UsernamePasswordCredentials(username, password));
//
//        RestClient restClient = RestClient.builder(HttpHost.create(elasticsearchUrl))
//            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
//            .build();
//
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
}

//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        RestClient restClient = RestClient.builder(HttpHost.create(elasticsearchUrl))
//            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
//                .setDefaultCredentialsProvider(credentialsProvider()))
//            .build();
//
//        RestClientTransport transport = new RestClientTransport(
//            restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }
//
//    private CredentialsProvider credentialsProvider() {
//        CredentialsProvider provider = new BasicCredentialsProvider();
//        provider.setCredentials(AuthScope.ANY,
//            new UsernamePasswordCredentials(username, password));
//        return provider;



//    @Bean
//    public RestClient restClient() {
//        // Parse the URL to extract host and port
//        HttpHost host = HttpHost.create(elasticsearchUrl);
//
//        // Set default headers with basic authentication
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//            new UsernamePasswordCredentials(username, password));
//
//        return RestClient.builder(host)
//            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
//                .setDefaultCredentialsProvider(credentialsProvider))
//            .build();
//    }




