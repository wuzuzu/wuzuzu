package com.sparta.wuzuzu.domain.user.service;

import com.sparta.wuzuzu.domain.user.dto.SignUpRequest;
import com.sparta.wuzuzu.domain.user.dto.SignUpResponse;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.entity.UserRole;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        String confirmEmail = signUpRequest.getEmail();
        if (userRepository.findByEmail(confirmEmail) != null) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        if (!Objects.equals(signUpRequest.getPassword(), signUpRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder().
            email(signUpRequest.getEmail())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .userName(signUpRequest.getUserName())
            .address(signUpRequest.getAddress())
            .petName(signUpRequest.getPetName())
            .petType(signUpRequest.getPetType())
            .role(UserRole.USER)
            .build();

        userRepository.save(user);

        return new SignUpResponse(user.getUserId(), user.getEmail());
    }
}
