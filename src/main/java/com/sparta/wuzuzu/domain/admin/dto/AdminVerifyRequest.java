package com.sparta.wuzuzu.domain.admin.dto;

import lombok.Getter;

@Getter
public class AdminVerifyRequest {

    private String password;
    private String mail;
    private String verifyCode;
}
