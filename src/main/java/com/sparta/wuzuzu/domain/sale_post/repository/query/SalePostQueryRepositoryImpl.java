package com.sparta.wuzuzu.domain.sale_post.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.category.entity.QCategory;
import com.sparta.wuzuzu.domain.sale_post.dto.SalePostVo;
import com.sparta.wuzuzu.domain.user.entity.QUser;
import com.sparta.wuzuzu.domain.sale_post.entity.QSalePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SalePostQueryRepositoryImpl implements SalePostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSalePost salePost = QSalePost.salePost;
    private final QUser user = QUser.user;
    private final QCategory category = QCategory.category;
    @Override
    public SalePostVo findPostByPostId(Long postId) {
        return jpaQueryFactory
            .select(Projections.constructor(SalePostVo.class,
                salePost.salePostId,
                salePost.title,
                salePost.description,
                salePost.views,
                salePost.user.userName,
                salePost.category.name,
                salePost.status,
                salePost.goods,
                salePost.price,
                salePost.stock
            ))
            .from(salePost)
            .where(salePost.salePostId.eq(postId))
            .leftJoin(salePost.user)            // Fetch Join 으로 N+1 문제 해결
            .leftJoin(salePost.category)        // Fetch Join 으로 N+1 문제 해결
            .fetchOne();
    }
}