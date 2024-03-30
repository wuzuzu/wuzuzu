package com.sparta.wuzuzu.domain.post.repository.query;

import com.sparta.wuzuzu.domain.post.dto.PostVo;

public interface PostQueryRepository {

    public PostVo findPostByPostId(Long postId);
}
