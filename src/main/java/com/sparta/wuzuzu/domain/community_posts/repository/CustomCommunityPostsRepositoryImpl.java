package com.sparta.wuzuzu.domain.community_posts.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.community_posts.dto.CommunityPostsResponse;
import com.sparta.wuzuzu.domain.community_posts.entity.CommunityPost;
import com.sparta.wuzuzu.domain.community_posts.entity.QCommunityPost;
import com.sparta.wuzuzu.domain.user.entity.QUser;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class CustomCommunityPostsRepositoryImpl implements CustomCommunityPostsRepository {

    public static final String TITLE = "title";
    public static final String VIEWS = "views";
    public static final String LIKE_COUNT = "likeCount";
    public static final String CREATED_AT = "createdAt";

    private final JPAQueryFactory queryFactory;
    QUser user = QUser.user;

    public CustomCommunityPostsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CommunityPostsResponse> findByConditions(String keyword, String categoryName,
        Pageable pageable) {
        QCommunityPost communityPost = QCommunityPost.communityPost;
        QUser user = QUser.user;

        JPAQuery<CommunityPostsResponse> query = queryFactory
            .select(Projections.constructor(CommunityPostsResponse.class,
                communityPost.title,
                user.userName.as("userName"),
                communityPost.content,
                communityPost.likeCount,
                communityPost.views))
            .from(communityPost)
            .leftJoin(communityPost.user, user)
            .where(
                keywordIs(keyword),
                categoryIs(categoryName)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        Sort.Order order = pageable.getSort().iterator().next();

        // 정렬 조건을 생성
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(order);

        // 쿼리에 정렬 조건 적용
        query.orderBy(orderSpecifier);

        QueryResults<CommunityPostsResponse> results = query.fetchResults();

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

    private OrderSpecifier<?> getOrderSpecifier(Sort.Order order) {
        QCommunityPost communityPost = QCommunityPost.communityPost;

        if (TITLE.equals(order.getProperty())) {
            return new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                communityPost.title);
        } else if (VIEWS.equals(order.getProperty())) {
            return new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                communityPost.views);
        } else if (LIKE_COUNT.equals(order.getProperty())) {
            return new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                communityPost.likeCount);
        } else if (CREATED_AT .equals(order.getProperty())) {
            return new OrderSpecifier<>(order.isAscending() ? Order.ASC : Order.DESC,
                communityPost.createdAt);
        } else {
            // 예외 처리
            throw new IllegalArgumentException(
                "해당 정렬기준은 제공하지 않습니다: " + order.getProperty());
        }
    }

}

