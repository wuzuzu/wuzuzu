package com.sparta.wuzuzu.domain.post.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.post.dto.StuffPostProjection;
import com.sparta.wuzuzu.domain.post.entity.QPost;
import com.sparta.wuzuzu.domain.stuff.entity.QStuff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QStuff stuff = QStuff.stuff;
    private final QPost post = QPost.post;

    @Override
    public StuffPostProjection findStuffAndPostByPostId(Long postId) {
        return jpaQueryFactory
            .select(Projections.constructor(StuffPostProjection.class,
                post.postId,
                post.title,
                post.content,
                post.status,
                post.views,
                stuff.name,
                stuff.description,
                stuff.price,
                stuff.stock,
                stuff.category))
            .from(post)
            .innerJoin(stuff).on(stuff.stuffId.eq(post.stuffId))
            .where(post.postId.eq(postId))
            .fetchOne();
    }
}
