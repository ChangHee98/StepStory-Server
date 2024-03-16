package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.User;
import lombok.Builder;

import java.util.Optional;


/**
 * DTO 계층 구현
 * 프롬엔티티 구현
 */
@Builder
public record FriendDetailDto (
        String nickname,
        String profileImgUrl,
        String selfIntro
){



    /**
     * 상세정보 확인 기능 전용 DTO
     */

    public static FriendDetailDto fromEntityDetails(User user) {
        return FriendDetailDto.builder()
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .selfIntro(user.getSelfIntro())
                .build();
    }




}
