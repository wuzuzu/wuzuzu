package com.sparta.wuzuzu.domain.community_posts.repository;

import com.sparta.wuzuzu.domain.community_posts.entity.Community_Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Community_CategoryRepository extends JpaRepository<Community_Category, Long> {

    Optional<Community_Category> findByNameEquals(String name);
}
