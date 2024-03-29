package com.sparta.wuzuzu.domain.post.repository.query;

import com.sparta.wuzuzu.domain.post.dto.PostProjection;
import com.sparta.wuzuzu.domain.user.entity.User;

public interface PostQueryRepository {

    public PostProjection findPostByPostId(Long postId);
}
