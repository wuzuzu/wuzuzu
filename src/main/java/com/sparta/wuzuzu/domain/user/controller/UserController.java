package com.sparta.wuzuzu.domain.user.controller;

import com.sparta.wuzuzu.domain.common.dto.CommonResponse;
import com.sparta.wuzuzu.domain.user.dto.LoginRequest;
import com.sparta.wuzuzu.domain.user.dto.SignUpRequest;
import com.sparta.wuzuzu.domain.user.dto.SignUpResponse;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.domain.user.service.UserService;
import com.sparta.wuzuzu.global.jwt.JwtUtil;
import com.sparta.wuzuzu.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<SignUpResponse>> signUp(@RequestBody @Validated SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = userService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.OK.value()).body(CommonResponse.<SignUpResponse>builder().data(signUpResponse).build());
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {

        try {
            Authentication authResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            HttpHeaders headers = new HttpHeaders();

            successfulAuthentication(authResult, headers);
            return ResponseEntity.status(HttpStatus.OK.value()).headers(headers).body(CommonResponse.<Void>builder().build());
        } catch (AuthenticationException e) {
            throw new AccountExpiredException("이메일, 비밀번호를 다시 확인해주세요");
        }
    }

    protected void successfulAuthentication(Authentication authResult, HttpHeaders headers) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);

        headers.add(JwtUtil.AUTHORIZATION_HEADER, token);
    }
}
