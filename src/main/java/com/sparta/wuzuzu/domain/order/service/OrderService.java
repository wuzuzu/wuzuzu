package com.sparta.wuzuzu.domain.order.service;

import com.sparta.wuzuzu.domain.order.dto.OrderRequest;
import com.sparta.wuzuzu.domain.order.dto.OrdersVo;
import com.sparta.wuzuzu.domain.order.entity.Order;
import com.sparta.wuzuzu.domain.order.repository.OrderRepository;
import com.sparta.wuzuzu.domain.order.repository.query.OrderQueryRepository;
import com.sparta.wuzuzu.domain.post.entity.Post;
import com.sparta.wuzuzu.domain.post.repository.PostRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final PostRepository postRepository;

    // 주문 요청 : redis 활용해서 재고 감소(QueryDsL 로 탐색) + 주문 내역 저장
    @Transactional
    public void createOrder(
        User user,
        OrderRequest requestDto
    ) {
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
            () -> new IllegalArgumentException("post is empty."));

        if(post.getStock() < requestDto.getCount()){
            throw new IllegalArgumentException("재고보다 주문 수량이 많습니다.");
        }

        // 동시성 제어 고려
        post.goodsOrder(requestDto.getCount());

        /*
        // 상품 주문시 재고가 모두 소진시 Post delete 처리 or 그대로 방치(재고 부족으로 남김)
        if(post.getStock().equals(0L)){
            post.delete();
        }
         */

        orderRepository.save(new Order(requestDto, user));
    }

    public List<OrdersVo> getOrders(
        User user
    ) {
        List<OrdersVo> orderList = orderQueryRepository.findAllOrders(user.getUserId());

        if(orderList.isEmpty()){
            throw new IllegalArgumentException("orderList is empty.");
        }

        return orderList;
    }
}
