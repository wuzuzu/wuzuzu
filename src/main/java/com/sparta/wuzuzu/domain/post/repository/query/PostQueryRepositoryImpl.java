package com.sparta.wuzuzu.domain.post.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.category.entity.QCategory;
import com.sparta.wuzuzu.domain.post.dto.PostVo;
import com.sparta.wuzuzu.domain.post.entity.QPost;

import com.sparta.wuzuzu.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;
    private final QUser user = QUser.user;
    private final QCategory category = QCategory.category;
    @Override
    public PostVo findPostByPostId(Long postId) {
        return jpaQueryFactory
            .select(Projections.constructor(PostVo.class,
                post.postId,
                post.title,
                post.description,
                post.views,
                post.user.userName,
                post.category.name,
                post.status,
                post.goods,
                post.price,
                post.stock
            ))
            .from(post)
            .where(post.postId.eq(postId))
            .leftJoin(post.user)            // Fetch Join 으로 N+1 문제 해결
            .leftJoin(post.category)        // Fetch Join 으로 N+1 문제 해결
            .fetchOne();
    }
}