package com.sparta.wuzuzu.domain.stuff.repository.query;

import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import java.util.List;

public interface StuffQueryRepository {
    List<Stuff> findAllById(Long userId);
}
