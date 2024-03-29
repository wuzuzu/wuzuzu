package com.sparta.wuzuzu.domain.dibs.service;

import com.sparta.wuzuzu.domain.dibs.dto.DibsProjection;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibsService {

    public void createDibs(User testUser, Long postId) {
    }

    public List<DibsProjection> getDibs(User testUser) {
        return null;
    }

    public void deleteDibs(User testUser, Long postId) {
    }
}
