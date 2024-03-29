package com.sparta.wuzuzu.domain.order.repository.query;

import com.sparta.wuzuzu.domain.order.dto.OrdersProjection;
import java.util.List;

public interface OrderQueryRepository {

    List<OrdersProjection> findAllOrders(Long userId);
}
