package com.sparta.wuzuzu.domain.stuff.service;

import com.sparta.wuzuzu.domain.stuff.dto.StuffRequest;
import com.sparta.wuzuzu.domain.stuff.dto.StuffResponse;
import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import com.sparta.wuzuzu.domain.stuff.repository.StuffRepository;
import com.sparta.wuzuzu.domain.stuff.repository.query.StuffQueryRepository;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StuffService {
    private final StuffRepository stuffRepository;
    private final StuffQueryRepository stuffQueryRepository;

    @Transactional
    public void createStuff(
        User user,
        StuffRequest requestDto
    ) {
        stuffRepository.save(new Stuff(requestDto, user));
    }

    public List<StuffResponse> getStuffs(
        User user
    ) {
        List<Stuff> stuffList = stuffRepository.findAllByUserOrderByModifiedAtDesc(user);  // Spring Data JPA Query Methods 방식
        // List<Stuff> stuffList = stuffQueryRepositoy.findAllById(user.getUserId());   // QueryDSL 방식

        if(stuffList.isEmpty()){
            throw new IllegalArgumentException("stuff is empty.");
        }

        return stuffList.stream()
            .map(StuffResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateStuff(
        User user,
        Long stuffId,
        StuffRequest requestDto
    ) {
        Stuff stuff = stuffRepository.findByUserAndStuffId(user, stuffId).orElseThrow(
            () -> new IllegalArgumentException("Stuff is empty."));
        stuff.update(requestDto);
    }
}
