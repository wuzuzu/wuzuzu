package com.sparta.wuzuzu.domain.order.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.order.dto.OrderRequest;
import com.sparta.wuzuzu.domain.order.dto.OrdersVo;
import com.sparta.wuzuzu.domain.order.service.OrderService;
import com.sparta.wuzuzu.domain.user.entity.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    User testUser = User.builder()
        .userId(1L)
        .email("test@test.com")
        .password("password")
        .userName("userName")
        .build();

    @PostMapping
    public ResponseEntity<Void> createOrder(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody OrderRequest requestDto
    ){
        orderService.createOrder(testUser, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<OrdersVo>>> getOrders(
        //@AuthenticationPrincipal UserDetailsImpl userDetails,
    ){
        List<OrdersVo> orderResponseList = orderService.getOrders(testUser);
        return CommonResponse.ofDataWithHttpStatus(orderResponseList, HttpStatus.CREATED);
    }
}

