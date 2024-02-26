package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.kcs.stepstory.dto.request.AuthSignUpDto;
import com.kcs.stepstory.dto.type.EProvider;
import com.kcs.stepstory.dto.type.ERole;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    /* Default Column */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_date")
    private LocalDate createdDate;

    /* User Info */
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_name")
    private String profileImageName = "default_profile.png";

    /* User Status */
    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private Boolean isLogin;

    @Column(name = "refresh_Token")
    private String refreshToken;

    @Column(name = "device_token")
    private String deviceToken;

    @Builder
    public User(String serialId, String password, EProvider provider, ERole role) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdDate = LocalDate.now();
        this.profileImageName = "default_profile.png";
        this.isLogin = false;
    }

    public void register(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = ERole.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateInfo(String nickname, String phoneNumber, String profileImageName) {
        if (nickname != null && (!Objects.equals(this.nickname, nickname))) {
            this.nickname = nickname;
        }

        if (phoneNumber != null && (!Objects.equals(this.phoneNumber, phoneNumber))) {
            this.phoneNumber = phoneNumber;
        }

        if (profileImageName != null && (!Objects.equals(this.profileImageName, profileImageName))) {
            this.profileImageName = profileImageName;
        }
    }

    public static User signUp(AuthSignUpDto authSignUpDto, String encodedPassword) {
        User user = User.builder()
                .serialId(authSignUpDto.serialId())
                .password(encodedPassword)
                .provider(EProvider.DEFAULT)
                .role(ERole.USER)
                .build();

        user.register(authSignUpDto.nickname(), authSignUpDto.phoneNumber());

        return user;
    }
}
