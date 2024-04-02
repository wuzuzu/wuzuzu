package com.sparta.wuzuzu.domain.post.repository;

import com.sparta.wuzuzu.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatusTrue();
}
