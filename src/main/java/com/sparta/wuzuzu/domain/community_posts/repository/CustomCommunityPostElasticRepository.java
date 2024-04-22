package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityElasticResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommunityPostElasticRepository {
    Page<CommunityElasticResponse> findByTitleContaining(String keyword, Pageable pageable);

}
