package com.sparta.wuzuzu.domain.community_posts.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.QCommunityPost;
import com.sparta.wuzuzu.domain.user.entity.QUser;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class CustomCommunityPostsRepositoryImpl implements CustomCommunityPostsRepository {

    private final JPAQueryFactory queryFactory;
    QUser user = QUser.user;

    public CustomCommunityPostsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CommunityPostsResponse> findByConditions(String keyword, String categoryName, Pageable pageable) {
        QCommunityPost communityPost = QCommunityPost.communityPost;
        QUser user = QUser.user;

        QueryResults<CommunityPostsResponse> results = queryFactory
            .select(Projections.constructor(CommunityPostsResponse.class,
                communityPost.title,
                user.userName,
                communityPost.content,
                communityPost.likeCount,
                communityPost.views))
            .from(communityPost)
            .leftJoin(communityPost.user)
            .where(
                keywordIs(keyword),
                categoryIs(categoryName)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(communityPost.createdAt.desc())
            .fetchResults();

        List<CommunityPostsResponse> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression keywordIs(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return QCommunityPost.communityPost.title.containsIgnoreCase(keyword);
        }
        return null;
    }

    private BooleanExpression categoryIs(String categoryName) {
        if (StringUtils.hasText(categoryName)) {
            return QCommunityPost.communityPost.category.name.eq(categoryName);
        }
        return null;
    }
}

