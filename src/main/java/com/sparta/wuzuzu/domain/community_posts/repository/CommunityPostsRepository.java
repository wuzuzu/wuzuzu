package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CommunityPostsRepository extends JpaRepository<CommunityPosts, Long> {

    @Query("SELECT new com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse(b.title, size(b.likeList)) FROM CommunityPosts b")
    Page<CommunityPostsResponse> findAllProjectedBy(Pageable pageable);

}
