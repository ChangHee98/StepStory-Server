package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Friend;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.response.FriendDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {


    /**
     *  친구 목록 조회
     */
    @Query("select u.userId from Friend f join f.user2 u where f.user1= :userId and f.status = 1")
    List<Long> findBySendFriendList1(@Param("userId") Long userId);

    @Query("select u.userId from Friend f join f.user1 u where f.user2= :userId and f.status = 1")
    List<Long> findByReceiveFriendList1(@Param("userId") Long userId);

    /**
     * 친구요청 목록조회 기능
     */
    @Query("SELECT u.userId FROM Friend f JOIN f.user1 u WHERE f.user2 = :userId AND f.status = 0")
    List<Long> findByrequestFriendList(@Param("userId") Long userId);



    /**
     * 친구요청 목록 count 기능
     */
    @Query("SELECT COUNT(f) " +
            "FROM Friend f " +
            "WHERE f.user1 = :friendId AND f.user2 = :userId AND f.status = 0")
    Long countByRequestFriendList(@Param("userId") Long userId, @Param("friendId") Long friendId);


    /**
     *  친구 상세정보 확인 기능
     */
    @Query("select u.userId from Friend f join f.user2 u where f.user1= :userId and f.user2= :friendId and f.status = 1")
    Long findBySendFriendDetails(@Param("userId") Long userId, @Param("friendId") Long friendId);
    @Query("select u.userId from Friend f join f.user1 u where f.user2= :userId and f.user1= :userId and f.status = 1")
    Long findByReceiveFriendDetails(@Param("userId") Long userId, @Param("friendId") Long friendId);


    /**
     * 친구 닉네임 검색 기능
     * !! 닉네임 한글자라도 입력하면 반환해줘야하므로 like 추가
     */
    @Query("select FriendDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl) " +
            "from Friend f join f.user2 u " +
            "where f.user1 = :userId and f.status = 1 and u.nickname LIKE CONCAT('%', :friendNickname, '%')")
    List<FriendDto> findBySendFriendNicknameList(@Param("userId") Long userId, @Param("friendNickname") String friendNickname);

    @Query("SELECT FriendDto.fromNicknameAndProfileImgUrl(u.nickname, u.profileImgUrl) " +
            "FROM Friend f JOIN f.user1 u " +
            "WHERE f.user2 = :userId AND f.status = 1 AND u.nickname LIKE CONCAT('%', :friendNickname, '%')")
    List<FriendDto> findByReceiveFriendNicknameList(@Param("userId") Long userId, @Param("friendNickname") String friendNickname);



    /**
     *  친구 요청 기능
     */
    @Modifying
    @Query("insert into Friend f (f.user1, f.user2, f.status) values (:userId1, :friendId, :status)")
    void insertFriendByUserId(@Param("userId1") Long userId1, @Param("friendId") Long friendId, @Param("status") int status);


    /**
     *  친구수락 기능
     */
    @Modifying
    @Query("update Friend f set f.status = 1 WHERE f.user1 = :friendId AND f.user2 = :userId")
    void acceptFriendRequest(@Param("userId") Long userId, @Param("friendId") Long friendId);




    /**
     *  친구삭제 기능 OR 친구요청 거절 기능
     *  *이랑 같은 의미로 하나의 행에 모든 컬럼을 반환하겠다. 이지만
     *  지금 f로 반환하는 이유는 -> 엔티티 타입으로 반환이 필요하기 때문
     */

//    @Query("select * from Friend f where (f.user1 = :userId and f.user2 = :friendId")
//    Friend findIdByUser2Id(@Param("userId") Long userId, @Param("friendId") Long friendId);
//
//    @Query("select * from Friend f where (f.user1 = :friendId and f.user2 = :userId")
//    Friend findIdByUser1Id(@Param("userId") Long userId, @Param("friendId") Long friendId);


    @Query("SELECT f FROM Friend f WHERE f.user1 = :userId AND f.user2 = :friendId")
    Friend findIdByUser2Id(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query("SELECT f FROM Friend f WHERE f.user1 = :friendId AND f.user2 = :userId")
    Friend findIdByUser1Id(@Param("userId") Long userId, @Param("friendId") Long friendId);





}