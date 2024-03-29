package com.sparta.wuzuzu.domain.order.service;

import com.sparta.wuzuzu.domain.order.dto.OrderRequest;
import com.sparta.wuzuzu.domain.order.dto.OrdersProjection;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    public void createOrder(User testUser, OrderRequest requestDto) {
    }

    public List<OrdersProjection> getOrders(User testUser) {
        return null;
    }
}
