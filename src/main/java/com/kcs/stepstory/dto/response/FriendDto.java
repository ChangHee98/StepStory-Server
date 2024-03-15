package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.User;
import lombok.Builder;

import java.util.Optional;


/**
 * DTO 계층 구현
 * 프롬엔티티 구현
 */
@Builder
public record FriendDto (
    String nickname,
    String profileImgUrl,
    String selfIntro
){



    /**
     * USER 엔티티 타입을 Dto 타입으로 변환 메서드
     * 만약 쿼리메서드가 User 엔티티 자체를 가지고 올 때,
     * 예시) select * from user where 특정 행 -> 즉 특정 모든 열값에 대한 하나의 행을 가지고 올때 사용되는 메서드
     */
    public static FriendDto fromEntity(User user) {
        return FriendDto.builder()
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }

    public static FriendDto fromEntityDetails(User user) {
        return FriendDto.builder()
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .selfIntro(user.getSelfIntro())
                .build();
    }

}
