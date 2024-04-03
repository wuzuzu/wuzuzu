package com.sparta.wuzuzu.domain.admin.controller;

import com.sparta.wuzuzu.domain.admin.dto.UserInformReadResponse;
import com.sparta.wuzuzu.domain.admin.service.AdminService;
import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.global.jwt.JwtTokenBlacklist;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;
    private final JwtTokenBlacklist jwtTokenBlacklist;

    @Secured("ADMIN")
    @GetMapping("/users")
    public ResponseEntity<CommonResponse<List<UserInformReadResponse>>> readAllUsers() {
        List<UserInformReadResponse> userList = adminService.readAllUsers();
        return CommonResponse.ofDataWithHttpStatus(userList, HttpStatus.OK);
    }

    @Secured("ADMIN")
    @GetMapping("/users/{userId}")
    public ResponseEntity<CommonResponse<UserInformReadResponse>> readAllUsers(@PathVariable Long userId) {
        UserInformReadResponse user = adminService.readUser(userId);
        return CommonResponse.ofDataWithHttpStatus(user, HttpStatus.OK);
    }

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
