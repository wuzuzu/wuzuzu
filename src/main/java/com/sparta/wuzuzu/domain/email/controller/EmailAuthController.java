package com.sparta.wuzuzu.domain.email.controller;

import com.sparta.wuzuzu.domain.admin.dto.AdminVerifyRequest;
import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.email.dto.EmailRequest;
import com.sparta.wuzuzu.domain.email.dto.VerifyRequest;
import com.sparta.wuzuzu.domain.email.service.EmailAuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    // 일반 사용자 인증
    @PostMapping("/auth")
    public ResponseEntity<CommonResponse<String>> sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailAuthService.sendEmail(emailRequest.getMail());
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.ofData("인증 메일 발송 성공"));
    }

    // 발송된 코드 인증
    @PostMapping("/verify")
    public ResponseEntity<CommonResponse<String>> verify(@RequestBody VerifyRequest verifyRequest){
        boolean isVerify = emailAuthService.verifyEmailCode(verifyRequest.getMail(), verifyRequest.getVerifyCode());
        return isVerify ? ResponseEntity.status(HttpStatus.OK).body(CommonResponse.ofData("코드 일치 인증 성공")) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.ofData("코드 불일치 인증 실패"));
    }

    // 관리자 권한 인증
    @PostMapping("/admins/{adminId}/auth")
    public ResponseEntity<CommonResponse<String>> requestAdminAuth(@PathVariable Long adminId, @RequestBody EmailRequest emailRequest) throws MessagingException {
        emailAuthService.sendAdminEmail(adminId, emailRequest.getMail());
        return CommonResponse.ofDataWithHttpStatus("관리자 인증 요청 이메일 인증 코드 발송", HttpStatus.OK);
    }

    @PatchMapping("/admins/{adminId}/verify")
    public ResponseEntity<CommonResponse<String>> responseAdminVerify(@PathVariable Long adminId, @RequestBody AdminVerifyRequest adminVerifyRequest){
        String wuzuzuPassword = emailAuthService.verifyAdminEmailCode(adminId, adminVerifyRequest);
        return CommonResponse.ofDataWithHttpStatus(wuzuzuPassword, HttpStatus.OK);
    }
}
