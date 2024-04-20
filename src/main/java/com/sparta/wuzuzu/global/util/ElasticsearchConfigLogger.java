package com.sparta.wuzuzu.global.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchConfigLogger {

    @Value("${spring.elasticsearch.rest.uris}")
    private String elasticsearchUri;

    @PostConstruct
    public void logConfig() {
        System.out.println("Configured Elasticsearch URI: " + elasticsearchUri);
    }
}
