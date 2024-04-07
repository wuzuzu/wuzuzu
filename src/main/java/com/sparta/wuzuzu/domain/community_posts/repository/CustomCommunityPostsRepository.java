package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCommunityPostsRepository {

    Page<CommunityPostsResponse> findByConditions(String keyword, String categoryName, Pageable pageable);

}
