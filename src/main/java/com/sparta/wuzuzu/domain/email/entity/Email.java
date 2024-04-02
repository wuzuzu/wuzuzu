package com.sparta.wuzuzu.domain.email.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TINYINT(1) default 0", nullable = false)
    private boolean authStatus;

    @Column(nullable = false)
    private Long userId;
}
