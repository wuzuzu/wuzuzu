package com.sparta.wuzuzu.domain.post.repository.query;

import com.sparta.wuzuzu.domain.post.dto.StuffPostProjection;

public interface PostQueryRepository {

    public StuffPostProjection findStuffAndPostByPostId(Long postId);
}
