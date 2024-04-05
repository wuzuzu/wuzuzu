package com.sparta.wuzuzu.domain.email.controller;

import com.sparta.wuzuzu.domain.admin.dto.AdminVerifyRequest;
import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.email.dto.EmailRequest;
import com.sparta.wuzuzu.domain.email.dto.VerifyRequest;
import com.sparta.wuzuzu.domain.email.service.EmailAuthService;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @Secured("ROLE_BEFORE_USER")
    @PostMapping("/users/{userId}/auth")
    public ResponseEntity<CommonResponse<String>> sendEmail(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId) throws MessagingException {
        emailAuthService.sendEmail(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.ofData("인증 메일 발송 성공"));
    }

    // 발송된 코드 인증
    @Secured("ROLE_BEFORE_USER")
    @PostMapping("/users/{userId}/verify")
    public ResponseEntity<CommonResponse<String>> verify(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId, @RequestBody VerifyRequest verifyRequest) {
        boolean isVerify = emailAuthService.verifyEmailCode(userDetails.getUser(), verifyRequest.getVerifyCode());
        return isVerify ? ResponseEntity.status(HttpStatus.OK).body(CommonResponse.ofData("코드 일치 인증 성공")) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.ofData("코드 불일치 인증 실패"));
    }

    // 관리자 권한 인증
    @PostMapping("/admins/{adminId}/auth")
    public ResponseEntity<CommonResponse<String>> requestAdminAuth(@PathVariable Long adminId, @RequestBody EmailRequest emailRequest) throws MessagingException {
        emailAuthService.sendAdminEmail(adminId, emailRequest.getMail());
        return CommonResponse.ofDataWithHttpStatus("관리자 인증 요청 이메일 인증 코드 발송", HttpStatus.OK);
    }

    @PatchMapping("/admins/{adminId}/verify")
    public ResponseEntity<CommonResponse<String>> responseAdminVerify(@PathVariable Long adminId, @RequestBody AdminVerifyRequest adminVerifyRequest) {
        String wuzuzuPassword = emailAuthService.verifyAdminEmailCode(adminId, adminVerifyRequest);
        return CommonResponse.ofDataWithHttpStatus(wuzuzuPassword, HttpStatus.OK);
    }
}
