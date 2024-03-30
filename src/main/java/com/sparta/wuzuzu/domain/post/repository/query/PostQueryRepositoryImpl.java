package com.sparta.wuzuzu.domain.post.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.post.dto.PostVo;
import com.sparta.wuzuzu.domain.post.entity.QPost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private final QPost post = QPost.post;
    @Override
    public PostVo findPostByPostId(Long postId) {
        return jpaQueryFactory
            .select(Projections.constructor(PostVo.class,
                post.postId,
                post.title,
                post.description,
                post.views,
                post.user.userName,
                post.status,
                post.goods,
                post.price,
                post.stock
            ))
            .from(post)
            .where(post.postId.eq(postId))
            .fetchOne();
    }
}