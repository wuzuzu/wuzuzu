package com.sparta.wuzuzu.domain.admin.controller;

import com.sparta.wuzuzu.domain.admin.service.AdminService;
import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.global.jwt.JwtTokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final JwtTokenBlacklist jwtTokenBlacklist;

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<String>> logout(HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        if (token != null) {
            jwtTokenBlacklist.addToBlacklist(token);
            return CommonResponse.ofDataWithHttpStatus("로그아웃 되었습니다.", HttpStatus.NO_CONTENT);
        } else {
            return CommonResponse.ofDataWithHttpStatus("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
