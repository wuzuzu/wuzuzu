package com.sparta.wuzuzu.domain.order.service;

import com.sparta.wuzuzu.domain.order.dto.OrderRequest;
import com.sparta.wuzuzu.domain.order.dto.OrdersVo;
import com.sparta.wuzuzu.domain.order.entity.Order;
import com.sparta.wuzuzu.domain.order.repository.OrderRepository;
import com.sparta.wuzuzu.domain.order.repository.query.OrderQueryRepository;
import com.sparta.wuzuzu.domain.sale_post.entity.SalePost;
import com.sparta.wuzuzu.domain.sale_post.repository.SalePostRepository;
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
    private final SalePostRepository salePostRepository;

    // 주문 요청 : redis 활용해서 재고 감소(QueryDsL 로 탐색) + 주문 내역 저장
    @Transactional
    public void createOrder(
        User user,
        OrderRequest requestDto
    ) {
        SalePost salePost = salePostRepository.findById(requestDto.getSalePostId()).orElseThrow(
            () -> new IllegalArgumentException("post is empty."));

        if(salePost.getStock() < requestDto.getCount()){
            throw new IllegalArgumentException("재고보다 주문 수량이 많습니다.");
        }

        // 동시성 제어 고려
        salePost.goodsOrder(requestDto.getCount());

        /*
        // 상품 주문시 재고가 모두 소진시 Post delete 처리 or 그대로 방치(재고 부족으로 남김)
        if(salePost.getStock().equals(0L)){
            salePost.delete();
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
