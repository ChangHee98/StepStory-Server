package com.kcs.stepstory.service;


import com.kcs.stepstory.domain.Friend;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.response.FriendDto;
import com.kcs.stepstory.dto.response.FriendListDto;
import com.kcs.stepstory.repository.FriendRepository;
import com.kcs.stepstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsService {


    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    /**
     *  친구 목록 조회 서비스 + 친구요청 목록 조회
     *  베어러 토큰의 user id가 파라미터로 받는다.
     */
    public FriendListDto getAllFriendList(Long userId) {

        //u.userid를 List 컬렉션에 저장
        List<Long> sendFriendIdList = friendRepository.findBySendFriendList1(userId);
        List<Long> receiveFriendIdList = friendRepository.findByReceiveFriendList1(userId);
        List<FriendDto> friendDtoList = new ArrayList<>();


        // u.userid를  fromEntity메소드를 통해
        for (int i = 0; i < sendFriendIdList.size(); i++) {
            //sendFriendIdList를 통해 User엔티티 타입의 객체 반환
            User user = userRepository.getReferenceById(sendFriendIdList.get(i));
            //fromEntity()이용하여 User 객체를 Dto타입으로 변환
            FriendDto friendDto = FriendDto.fromEntity(user);
            friendDtoList.add(friendDto);
        }
        for (Long id : receiveFriendIdList) {
            //userid > User 엔티티
            User user = userRepository.getReferenceById(id);
            //엔티티를 -> dto로 변환
            FriendDto friendDto = FriendDto.fromEntity(user);
            friendDtoList.add(friendDto);
        }

        List<Long> requestFriendList = friendRepository.findByrequestFriendList(userId);
        List<FriendDto> requestFriendDtoList = new ArrayList<>();

        for (Long requestId : requestFriendList) {
            User user = userRepository.getReferenceById(requestId);
            FriendDto friendDto = FriendDto.fromEntity(user);
        }
        return FriendListDto.builder()
                .friendListDtos(friendDtoList)
                .requestfriendListDtos(requestFriendDtoList) // 친구 요청 목록 추가
                .build();
    }



    /**
     * 친구요청 목록 Count 서비스
     */
    public Long getCountFriendList(Long userId, Long friendId) {
        Long requestFriendList = friendRepository.countByRequestFriendList(userId, friendId);

        return requestFriendList;
    }


    /**
     * 상세 정보 확인 서비스
     */
    //
    @Transactional
    public FriendDto getFriendDetailsUser(Long userId, Long friendId) {
        FriendDto friend = friendRepository.findBySendFriendDetails(userId, friendId);
        if(friend == null) {
            friend = friendRepository.findByReceiveFriendDetails(userId, friendId);
        }

        return friend;
    }


    /**
     *  친구닉네임 조회 서비스
     */
    public FriendListDto getFriendNickNameList(Long userId, String nickName) {
        List<FriendDto> SendFriendNicknameList = friendRepository.findBySendFriendNicknameList(userId, nickName);
        List<FriendDto> ReceiveFriendNicknameList = friendRepository.findByReceiveFriendNicknameList(userId, nickName);

        List<FriendDto> combinedFriendNicknameList = new ArrayList<>(SendFriendNicknameList);
        combinedFriendNicknameList.addAll(ReceiveFriendNicknameList);

        return FriendListDto.builder().friendListDtos(combinedFriendNicknameList).build();
    }

    /**
     *  친구 요청 서비스
     */
    @Transactional
    public void requestFriendsUser(Long userId, Long friendId, int status) {
        friendRepository.insertFriendByUserId(userId, friendId, status);
    }

    /**
     *  친구 수락 서비스
     */
    @Transactional
    public void acceptFriendsUser(Long userId, Long friendId) {
        // friendRepository를 이용해 userId와 friendId로 Friend 객체 하나를 찾음
        /**
         * Friend friend = friendRepository.find();
         * friend.makeFriendRelation();
         */
        friendRepository.acceptFriendRequest(userId, friendId);
    }

    /**
     *  친구 삭제 서비스 OR 친구 거절 서비스
     */
    @Transactional
    public void deleteFriendsUser(Long userId, Long friendId) {
        Friend friend = friendRepository.findIdByUser2Id(userId, friendId);
        if(friend == null) {
            friend = friendRepository.findIdByUser1Id(userId, friendId);
        }

        // friend = 엔티티로 반환된 값 대입 후 f.엔티티id를 찾아서 deleteById 메서드 실행
        friendRepository.deleteById(friend.getFriendId());

    }



}
