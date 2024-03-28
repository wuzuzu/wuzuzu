package com.sparta.wuzuzu.domain.user.entity;

import com.sparta.wuzuzu.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column
    private String address;

    @Column
    private String petName;

    @Column
    private String petType;

    @Builder
    public User(String email, String password, String userName, String address, String petName, String petType) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.address = address;
        this.petName = petName;
        this.petType = petType;
    }
}
