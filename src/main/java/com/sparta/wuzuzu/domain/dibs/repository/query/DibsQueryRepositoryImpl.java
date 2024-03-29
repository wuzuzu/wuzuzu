package com.sparta.wuzuzu.domain.dibs.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.dibs.dto.DibsProjection;
import com.sparta.wuzuzu.domain.dibs.entity.QDibs;
import com.sparta.wuzuzu.domain.post.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DibsQueryRepositoryImpl implements DibsQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;
    private final QDibs dibs = QDibs.dibs;

    @Override
    public List<DibsProjection> findAllDibs(Long userId) {
        return jpaQueryFactory
            .select(Projections.constructor(DibsProjection.class,
                post.postId,
                post.title,
                post.views,
                post.user.userName,
                post.status,
                post.goods,
                post.price,
                post.stock))
            .from(dibs)
            .join(post).on(dibs.postId.eq(post.postId))
            .where(dibs.user.userId.eq(userId))
            .orderBy(dibs.modifiedAt.desc())
            .fetch();
    }
}