package com.sparta.wuzuzu.domain.stuff.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.wuzuzu.domain.stuff.entity.QStuff;
import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StuffQueryRepositoryImpl implements StuffQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QStuff stuff = QStuff.stuff;

    @Override
    public List<Stuff> findAllById(Long userId) {
        return jpaQueryFactory
            .select(stuff)
            .from(stuff)
            .where(stuff.user.userId.eq(userId))
            .orderBy(stuff.modifiedAt.desc())
            .fetch();
    }
}
