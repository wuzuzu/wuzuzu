package com.sparta.wuzuzu.domain.email.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.email.dto.EmailRequest;
import com.sparta.wuzuzu.domain.email.service.EmailAuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @PostMapping("/auth")
    public ResponseEntity<CommonResponse<String>> sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailAuthService.sendEmail(emailRequest.getMail());
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.ofData("인증 메일 발송 성공"));
    }
}
