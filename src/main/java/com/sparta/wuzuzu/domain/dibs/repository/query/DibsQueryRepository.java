package com.sparta.wuzuzu.domain.dibs.repository.query;

import com.sparta.wuzuzu.domain.dibs.dto.DibsProjection;
import java.util.List;

public interface DibsQueryRepository {
    List<DibsProjection> findAllDibs(Long userId);
}
