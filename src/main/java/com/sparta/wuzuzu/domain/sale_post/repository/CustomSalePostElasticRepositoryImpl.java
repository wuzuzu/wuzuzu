package com.sparta.wuzuzu.domain.sale_post.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.wuzuzu.domain.sale_post.dto.SalePostElasticResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CustomSalePostElasticRepositoryImpl implements CustomSalePostElasticRepository {

    private final ElasticsearchClient elasticsearchClient;

    public CustomSalePostElasticRepositoryImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public Page<SalePostElasticResponse> findByTitleAndGoodsContaining(String keyword,
        Pageable pageable) {
        try {
            SearchRequest request = new SearchRequest.Builder()
                .index("sale_posts")
                .query(q -> q
                    .bool(b -> b
                        .should(m -> m
                            .match(mm -> mm
                                .field("title")
                                .query(keyword)
                            )
                        )
                        .should(m -> m
                            .match(mm -> mm
                                .field("goods")
                                .query(keyword)
                            )
                        )
                        .should(m -> m
                            .match(mm -> mm
                                .field("category.name")  // 카테고리 이름에서 검색 추가
                                .query(keyword)
                            )
                        )
                        .minimumShouldMatch("1")  // 적어도 하나의 조건을 만족
                    )
                )
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
                .build();

            SearchResponse<Map> response = elasticsearchClient.search(request, Map.class);
            List<SalePostElasticResponse> content = response.hits().hits().stream()
                .map(hit -> mapToDto((Map<String, Object>) hit.source()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            return new PageImpl<>(content, pageable, response.hits().total().value());
        } catch (Exception e) {
            log.error("Failed to search in Elasticsearch", e);
            throw new RuntimeException("Failed to search in Elasticsearch", e);
        }
    }
    private SalePostElasticResponse mapToDto(Map<String, Object> source) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(source, SalePostElasticResponse.class);
    }

}
