package com.sparta.wuzuzu.domain.community_posts.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityElasticResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPost;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CustomCommunityPostElasticRepositoryImpl implements CustomCommunityPostElasticRepository {

    private final ElasticsearchClient elasticsearchClient;

    public CustomCommunityPostElasticRepositoryImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

//    @Override
//    public Page<CommunityElasticResponse> findByTitleContaining(String keyword, Pageable pageable) {
//        try {
//            SearchRequest request = new SearchRequest.Builder()
//                .index("community_posts")
//                .query(q -> q
//                    .match(m -> m
//                        .field("title")
//                        .query(keyword)
//                    )
//                )
//                .from((int) pageable.getOffset())
//                .size(pageable.getPageSize())
//                .build();
//
//            SearchResponse<Map> response = elasticsearchClient.search(request, Map.class);
//            List<CommunityElasticResponse> content = response.hits().hits().stream()
//                .map(hit -> mapToDto((Map<String, Object>) hit.source()))
//                .collect(Collectors.toList());
//
//            return new PageImpl<>(content, pageable, response.hits().total().value());
//        } catch (Exception e) {
//            log.error("Failed to convert map to DTO: {}", source, e);
//            throw new RuntimeException("Failed to search in Elasticsearch", e);
//        }
//    }
//
//    private CommunityElasticResponse mapToDto(Map<String, Object> source) {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.convertValue(source, CommunityElasticResponse.class);
//    }
    @Override
    public Page<CommunityElasticResponse> findByTitleContaining(String keyword, Pageable pageable) {
        try {
            SearchRequest request = new SearchRequest.Builder()
                .index("community_posts")
                .query(q -> q
                    .match(m -> m
                        .field("title")
                        .query(keyword)
                    )
                )
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .build();

            SearchResponse<Map> response = elasticsearchClient.search(request, Map.class);
            List<CommunityElasticResponse> content = response.hits().hits().stream()
                .map(hit -> {
                    try {
                        return mapToDto((Map<String, Object>) hit.source());
                    } catch (Exception e) {
                        log.error("Failed to convert map to DTO: {}", hit.source(), e);
                        return null; // 또는 예외를 재발생시키지 않고 기본값을 반환
                    }
                })
                .filter(Objects::nonNull) // null이 아닌 결과만 수집
                .collect(Collectors.toList());

            return new PageImpl<>(content, pageable, response.hits().total().value());
        } catch (Exception e) {
            log.error("Failed to search in Elasticsearch", e);
            throw new RuntimeException("Failed to search in Elasticsearch", e);
        }
    }

    private CommunityElasticResponse mapToDto(Map<String, Object> source) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(source, CommunityElasticResponse.class);
    }
}
