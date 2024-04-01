package com.sparta.wuzuzu.domain.user.service;

import com.sparta.wuzuzu.domain.user.dto.MyInformReadResponse;
import com.sparta.wuzuzu.domain.user.dto.MyInformUpdateRequest;
import com.sparta.wuzuzu.domain.user.entity.User;
import com.sparta.wuzuzu.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MyInformService {

    private final UserRepository userRepository;

    public MyInformReadResponse readMyInform(User user) {
        user = userRepository.findById(user.getUserId()).orElseThrow();
        return MyInformReadResponse.builder()
            .user(user)
            .build();
    }

    public MyInformReadResponse updateMyInform(User user, MyInformUpdateRequest myInformUpdateRequest) {
        user = userRepository.findById(user.getUserId()).orElseThrow();
        user.update(user, myInformUpdateRequest);
        return MyInformReadResponse.builder()
            .user(user)
            .build();
    }
}
