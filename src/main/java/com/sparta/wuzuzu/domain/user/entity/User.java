package com.sparta.wuzuzu.domain.user.entity;

import com.sparta.wuzuzu.domain.common.entity.Timestamped;
import com.sparta.wuzuzu.domain.user.dto.MyInformUpdateRequest;
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String email, String password, String userName, String address, String petName, String petType, UserRole role) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.address = address;
        this.petName = petName;
        this.petType = petType;
        this.role = role;
    }

    public void update(User user, MyInformUpdateRequest myInformUpdateRequest) {
        user.address = myInformUpdateRequest.getAddress();
        user.userName = myInformUpdateRequest.getUserName();
        user.petName = myInformUpdateRequest.getPetName();
        user.petType = myInformUpdateRequest.getPetType();
    }

    public void updatePassword(User user, String passwordToEncrypt) {
        user.password = passwordToEncrypt;
    }
}
