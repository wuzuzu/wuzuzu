package com.sparta.wuzuzu.domain.order.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.order.dto.OrdersVo;
import com.sparta.wuzuzu.domain.order.entity.QOrder;
import com.sparta.wuzuzu.domain.post.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;
    private final QOrder order = QOrder.order;

    @Override
    public List<OrdersVo> findAllOrders(Long userId) {
        return jpaQueryFactory
            .select(Projections.constructor(OrdersVo.class,
                post.postId,
                post.title,
                post.views,
                post.user.userName,
                post.category.name,
                post.status,
                post.goods,
                post.price,
                order.count))
            .from(order)
            .join(post).on(order.postId.eq(post.postId))
            .where(order.user.userId.eq(userId))
            .orderBy(order.modifiedAt.desc())
            .fetch();
    }
}