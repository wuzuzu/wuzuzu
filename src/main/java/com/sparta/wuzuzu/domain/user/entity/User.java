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

    @Column(columnDefinition = "TINYINT(1) default 0")
    private Boolean blocked;

    @Column(nullable = false)
    private int numberOfCount;

    @Builder
    public User(String email, String password, String userName,
                String address, String petName, String petType,
                UserRole role, Boolean blocked, int numberOfCount) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.address = address;
        this.petName = petName;
        this.petType = petType;
        this.role = role;
        this.blocked = blocked;
        this.numberOfCount = numberOfCount;
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

    public void beBlocked(User user, Boolean blocked){
        user.blocked = blocked;
    }

    public void plusCount(User user){
        user.numberOfCount += 1;
    }
}
