package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.User;
import lombok.Builder;


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
     * 친구 목록 조회 OR 친구요청 목록조회 기능 친구 닉네임 검색 기능에 대한 DTO변환 메서드
     * fromNicknameAndProfileImgUrl 메소드 -> nickname, profileImgUrl 만 조회하는 경우 수행
     */
    public static FriendDto fromNicknameAndProfileImgUrl(String nickname, String profileImgUrl){
        return FriendDto.builder().
                nickname(nickname).
                profileImgUrl(profileImgUrl).build();
    }

    /**
     * 친구 상세정보 기능에 대한 DTO 변환 메서드
     */
    public static FriendDto fromNicknameAndProfileImgUrlAndSelfIntro(String nickname, String profileImgUrl, String selfIntro){
        return FriendDto.builder().
                nickname(nickname).
                profileImgUrl(profileImgUrl).
                selfIntro(selfIntro).
                build();
    }



    /**
     * USER 엔티티 타입받을 상황 대비해 확장한 메서드
     * 만약 쿼리메서드가 User 엔티티 자체를 가지고 올 때,
     * 예시) select * from user where 특정 행 -> 즉 특정 모든 열값에 대한 하나의 행을 가지고 올때 사용되는 메서드
     */
    public static FriendDto fromEntity(User user){
        return FriendDto.builder().
                nickname(user.getNickname()).
                profileImgUrl(user.getProfileImgUrl()).build();
    }



}
