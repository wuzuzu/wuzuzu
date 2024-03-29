package com.sparta.wuzuzu.domain.post.service;

import com.sparta.wuzuzu.domain.post.dto.PostRequest;
import com.sparta.wuzuzu.domain.post.dto.PostResponse;
import com.sparta.wuzuzu.domain.post.dto.StuffPostProjection;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    public void createPost(User testUser, PostRequest requestDto) {
    }

    public List<PostResponse> getPosts() {
        return null;
    }

    public StuffPostProjection getPost(Long postId) {
        return null;
    }

    public void updatePost(User testUser, Long postId, PostRequest requestDto) {
    }

    public void deletePost(User testUser, Long postId) {
    }
}
