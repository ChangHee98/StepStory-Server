package com.kcs.stepstory.controller;

import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.dto.global.ResponseDto;
import com.kcs.stepstory.dto.response.FriendDetailDto;
import com.kcs.stepstory.dto.response.FriendDto;
import com.kcs.stepstory.dto.response.FriendListDto;
import com.kcs.stepstory.dto.response.FriendSearchListDto;
import com.kcs.stepstory.service.FriendsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users/friend")
public class FriendsController {
    private FriendsService friendsService;


    /**
     * 친구 목록 조회 기능 + 친구요청 목록 조회 기능 매핑
     * ResponseDto ->  보일러 플레이트 기능 -> getAllFriendList 메소드 동작 후 ResponseDto라는 인터셉터 동작하여 상태코드를 추가하는 작업
     * @UserId는 베어러 토큰의 userid만 가져온다.
     */
    @GetMapping("")
    public ResponseDto<FriendListDto> getAllFriendList(@UserId Long userId) {
        return ResponseDto.ok(friendsService.getAllFriendList(userId));
    }

    /**
     * 친구요청 목록 Count 서비스 매핑
     */    @GetMapping("/count")
    public ResponseDto<Long> getrequestCount(@UserId Long userId) {
        return ResponseDto.ok(friendsService.getCountFriendList(userId));
    }



    /**
     * 친구 상세정보 확인 서비스 매핑
     */
    @GetMapping("/detail/{friendId}")
    public ResponseDto<FriendDetailDto> getFriendDetails(@UserId Long userId, @PathVariable("friendId") Long friendId) {
        FriendDetailDto friendDetails = friendsService.getFriendDetailsUser(userId, friendId);
        return ResponseDto.ok(friendDetails);
    }




    /**
     * 친구닉네임 검색 서비스 매핑
     */
    @GetMapping("/search/{nickname}")
    public ResponseDto<FriendSearchListDto> getFriend(@UserId Long userId, @PathVariable String nickname) {

        FriendSearchListDto requestFriendNickName = friendsService.getFriendNickNameList(userId, nickname);
        return ResponseDto.ok(requestFriendNickName);
    }


    /**
     * 친구 요청 기능 매핑
     */
    @PostMapping("")
    public Map<String, String> requestFriend(@UserId Long userId, @RequestBody Map<String, Long> requestBody) {
        Long friendId = requestBody.get("friendId");
        friendsService.requestFriendsUser(userId, friendId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "친구 요청이 성공적으로 전송되었습니다.");
        return response;
    }

    /**
     * 친구 수락 기능 매핑
     */
    @PatchMapping("")
    public Map<String, String> acceptFrined(@UserId Long userId, @RequestBody Map<String, Long> requestBody) {
        Long friendId = requestBody.get("friendId");
        friendsService.acceptFriendsUser(userId, friendId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "친구요청이 수락되었습니다.");
        return response;

    }


    /**
     * 친구 삭제 기능 OR 친구 거 절 기능 매핑
     */
    @DeleteMapping("/{friendId}")
    public Map<String, String> deleteFriend(@UserId Long userId, @PathVariable Long friendId) {
        friendsService.deleteFriendsUser(userId, friendId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "친구 삭제 완료되었습니다.");
        return response;
    }










}