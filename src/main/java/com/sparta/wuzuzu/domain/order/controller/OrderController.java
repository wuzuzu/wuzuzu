package com.sparta.wuzuzu.domain.order.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.order.dto.OrderRequest;
import com.sparta.wuzuzu.domain.order.dto.OrdersVo;
import com.sparta.wuzuzu.domain.order.service.OrderService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody OrderRequest requestDto
    ){
        orderService.createOrder(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<OrdersVo>>> getOrders(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        List<OrdersVo> orderResponseList = orderService.getOrders(userDetails.getUser());
        return CommonResponse.ofDataWithHttpStatus(orderResponseList, HttpStatus.CREATED);
    }
}

