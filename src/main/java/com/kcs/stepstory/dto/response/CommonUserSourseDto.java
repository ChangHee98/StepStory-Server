package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.User;
import lombok.Builder;

@Builder
public record CommonUserSourseDto(
        String nickname,
        String profileImgUrl
) {
    public static CommonUserSourseDto fromEntity(User user){
        return CommonUserSourseDto.builder()
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }
}
