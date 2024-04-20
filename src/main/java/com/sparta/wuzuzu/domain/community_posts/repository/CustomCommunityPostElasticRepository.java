package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityElasticResponse;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommunityPostElasticRepository {
    Page<CommunityElasticResponse> findByTitleContaining(String keyword, Pageable pageable);

}
