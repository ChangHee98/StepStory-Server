package com.kcs.stepstory.dto.response;


import lombok.*;

import java.util.List;

@Builder
/**
 *  FriendListDto recode는 FriendDto타입의 필드선언하여 FriendDto 객체의 리스트 형태로 저장한다.
 */
public record FriendListDto(
        List<FriendDto> friendListDtos,
        List<FriendDto> requestFriendListDtos
){}