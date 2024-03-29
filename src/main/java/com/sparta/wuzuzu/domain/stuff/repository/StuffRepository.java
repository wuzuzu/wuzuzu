package com.sparta.wuzuzu.domain.stuff.repository;

import com.sparta.wuzuzu.domain.stuff.entity.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuffRepository extends JpaRepository<Stuff, Long> {

}
