package com.sparta.wuzuzu.domain.order.service;

import com.sparta.wuzuzu.domain.order.dto.OrderRequest;
import com.sparta.wuzuzu.domain.order.dto.OrdersProjection;
import com.sparta.wuzuzu.domain.order.entity.Order;
import com.sparta.wuzuzu.domain.order.repository.OrderRepository;
import com.sparta.wuzuzu.domain.order.repository.query.OrderQueryRepository;
import com.sparta.wuzuzu.domain.post.entity.Post;
import com.sparta.wuzuzu.domain.post.repository.PostRepository;
import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import com.sparta.wuzuzu.domain.stuff.repository.StuffRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final PostRepository postRepository;
    private final StuffRepository stuffRepository;

    // 주문 요청 : redis 활용해서 재고 감소(QueryDsL 로 탐색) + 주문 내역 저장
    public void createOrder(
        User user,
        OrderRequest requestDto
    ) {
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
            () -> new IllegalArgumentException("post is empty."));

        Stuff stuff = stuffRepository.findById(post.getStuffId()).orElseThrow(
            () -> new IllegalArgumentException("stuff is empty."));

        if(stuff.getStock() < requestDto.getCount()){
            throw new IllegalArgumentException("재고보다 주문 수량이 많습니다.");
        }

        // 동시성 제어 고려
        stuff.stuffOrder(requestDto.getCount());

        orderRepository.save(new Order(requestDto, user));
    }

    public List<OrdersProjection> getOrders(
        User user
    ) {
        return orderQueryRepository.findAllOrders(user.getUserId());
    }
}
