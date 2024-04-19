package com.sparta.wuzuzu.domain.order.dto;

import com.sparta.wuzuzu.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderMessage {

    private User user;

    private OrderRequest request;

    private String imp_uid;

    private String merchant_uid;

    public CreateOrderMessage(User user, OrderRequest orderRequest, String imp_uid, String merchant_uid) {
        this.user = user;
        this.request = orderRequest;
        this.imp_uid = imp_uid;
        this.merchant_uid = merchant_uid;
    }
}
