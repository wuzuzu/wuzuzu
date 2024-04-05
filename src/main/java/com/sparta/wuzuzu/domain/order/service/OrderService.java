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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final SalePostRepository salePostRepository;
    private final RedisTemplate<String, Object> redisTemplate;

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

        // 동시성 제어를 위한 키
        String stockKey = "salePost:" + salePost.getSalePostId() + ":stock";

        // 트랜잭션 시작
        redisTemplate.watch(stockKey);  // multi

        try {
            // 현재 재고량 확인
            Object currentStockObj = redisTemplate.opsForValue().get(stockKey);
            Long currentStock = (currentStockObj != null) ? Long.parseLong(currentStockObj.toString()) : 0L;

            // 주문 가능 여부 확인
            if (currentStock < requestDto.getCount()) {
                System.out.println("재고가 부족합니다.");
//                throw new IllegalArgumentException("재고 부족");
            } else {
                // 주문 저장
                Order order = orderRepository.save(new Order(requestDto, user));

                // Redis에서 재고 감소
                Long newStock = redisTemplate.opsForValue().increment(stockKey, -requestDto.getCount());

                // 변경된 SalePost 객체를 repository에 저장
                salePost.updateStock(newStock);
                salePostRepository.save(salePost);

                System.out.println("재고 감소: " + newStock); // 감소된 재고 확인용 코드
            }

            // Redis 트랜잭션 실행
            redisTemplate.unwatch(); // exec

        } catch (OptimisticLockingFailureException ex) {
            // 동시성 제어 실패 처리
            throw new IllegalArgumentException("주문 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        } catch (DataAccessException ex) {
            // 데이터 액세스 예외 발생 시 롤백
            redisTemplate.discard();
            throw new IllegalArgumentException("주문 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
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
