package com.sparta.wuzuzu.domain.stuff.service;

import com.sparta.wuzuzu.domain.stuff.dto.StuffRequest;
import com.sparta.wuzuzu.domain.stuff.dto.StuffResponse;
import com.sparta.wuzuzu.domain.stuff.repository.StuffRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StuffService {
    private final StuffRepository stuffRepository;

    public void createStuff(User testUser, StuffRequest requestDto) {
    }

    public List<StuffResponse> getStuffs(User testUser) {
        return null;
    }

    public void updateStuff(User testUser, Long stuffId, StuffRequest requestDto) {
    }
}
