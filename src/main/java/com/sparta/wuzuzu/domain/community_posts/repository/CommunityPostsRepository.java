package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommunityPostsRepository extends JpaRepository<CommunityPosts, Long> {

    @Query("SELECT p FROM CommunityPosts p WHERE p.title LIKE %:keyword%")
    Page<CommunityPosts> findByTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT p FROM CommunityPosts p WHERE p.category.name = :categoryName")
    Page<CommunityPosts> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

}
