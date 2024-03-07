package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Friend;
import com.kcs.stepstory.dto.response.FriendDto;
import com.kcs.stepstory.dto.response.FriendListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {


    /**
     *  친구 목록 조회 & 친구 요청 목록 기능 & 친구 닉네임 검색 기능
     */

    @Query("select FriendListDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl) " +
            "from Friend f join f.user2 u  where f.user1 = :userId and f.status = 1")
    List<Friend> findBySendFriendList(@Param("userId") Long userId);


    @Query("select FriendListDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl) " +
            "from Friend f join f.user2 u  where f.user2 = :userId and f.status = 1")
    List<FriendDto> findByReceiveFriendList(@Param("userId") Long userId);

    // 친구요청 목록 기능
    @Query("select FriendListDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl)" +
            "from Friend f join f.user1 u  where f.user1 = :friendId, f.user2 = :userId and f.status = 1")
    List<Friend> findFriendRequestsByUserId(@Param("userId") Long userId, @Param("friendId") Long friendId, @Param("status") int status);


    /**
     *  친구 상세 정보 확인 기능
     */
    @Query("select FriendListDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl, u.selfIntro) " +
            "from Friend f JOIN f.user2 u  where f.user2 = :userId and f.status = 1")
    List<Friend> findFriendDetails(@Param("userId") Long userId, @Param("friendId") Long friendId, @Param("status") int status);



    /**
     *  친구 삭제 기능
     */
    @Modifying
    @Query("delete from Friend f where (f.user1 = :userId and f.user2 = :friendId or f.userId1 = :friendId and f.userId2 = :userId) and f.status = 1")
    void deleteFriendByUserId(@Param("userId") Long userId, @Param("friendId") Long friendId);


    /**
     *  친구 요청 기능
     */
    @Modifying
    @Query("insert into Friend f (f.user1, f.user2, f.status) values (:userId1, :friendId, :status)")
    void insertFriendByUserId(@Param("userId1") Long userId1, @Param("friendId") Long friendId, @Param("status") int status);



    /**
     *  친구 수락 기능
     */
    @Modifying
    @Query("update Friend f set f.status = 1 WHERE f.user1 = :friendId AND f.user2 = :userId")
    void acceptFriendRequest(@Param("userId") Long userId, @Param("friendId") Long friendId);


    /**
     *  친구요청 거절 기능
     */
    @Modifying
    @Query("delete from Friend f where (f.user1 = :friendId and f.user2 = :userId AND f.status = 0")
    void refuseFriendByUserId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    /**
     * 친구 닉네임 검색 기능
     */
    @Query("select FriendDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl) " +
            "from Friend f join f.user2 u  where f.user1 = :userId and f.user2 = :friendId f.status = 1 or f.user1 = :friendId and f.user2 = :userId  f.status = 1")
    FriendDto findByFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    /**
     * 친구요청 목록조회 기능
     */
    @Query("select FriendDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl) " +
            "from Friend f join f.user2 u where f.user1 = :friendId and f.user2 = :userId and f.status =0")
    List<Friend> findBySendFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);


}