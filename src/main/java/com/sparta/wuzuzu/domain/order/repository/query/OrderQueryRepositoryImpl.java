package com.sparta.wuzuzu.domain.order.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.order.dto.OrdersProjection;
import com.sparta.wuzuzu.domain.order.entity.QOrder;
import com.sparta.wuzuzu.domain.post.entity.QPost;
import com.sparta.wuzuzu.domain.stuff.entity.QStuff;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepositoryImpl implements OrderQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QStuff stuff = QStuff.stuff;
    private final QPost post = QPost.post;
    private final QOrder order = QOrder.order;

    public List<OrdersProjection> findAllOrders(Long userId){
        return jpaQueryFactory
            .select(Projections.constructor(OrdersProjection.class,
                post.postId,
                post.title,
                post.content,
                post.status,
                post.views,
                stuff.stuffId,
                stuff.name,
                stuff.description,
                stuff.price,
                stuff.category))
            .from(order)
            .join(post).on(order.postId.eq(post.postId))
            .join(stuff).on(post.stuffId.eq(stuff.stuffId))
            .where(order.user.userId.eq(userId))
            .orderBy(order.modifiedAt.desc())
            .fetch();
    }
}
