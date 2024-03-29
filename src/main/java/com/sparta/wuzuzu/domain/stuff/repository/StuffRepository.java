package com.sparta.wuzuzu.domain.stuff.repository;

import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import com.sparta.wuzuzu.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuffRepository extends JpaRepository<Stuff, Long> {
    Optional<Stuff> findByUserAndStuffId(User user, Long stuffId);

    List<Stuff> findAllByUserOrderByModifiedAtDesc(User user);   // QueryDSL 과 비교하기 위함
}
