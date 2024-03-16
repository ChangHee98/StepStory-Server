package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Step;
import com.kcs.stepstory.domain.StepId;
import com.kcs.stepstory.dto.response.FriendDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyStepRepository extends JpaRepository<Step, StepId> {




    /**
     *  발자국 조회 - 전체
     *  특정 user에 대한 모든 객체 반환
     */
    @Query("SELECT s FROM Step s WHERE s.user.id = :userId")
    List<Step> findByAllAreaListForMyStep(@Param("userId") Long userId);




    /**
     *  발자국 조회 - 서울 + 경기
     *  특정 user에 대한 모든 객체 반환
     */
    @Query("SELECT s FROM Step s WHERE s.user.id = :userId AND s.province = :province")
    List<Step> findBySeoulListForMyStep(@Param("userId") Long userId, @Param("province") String province);


}


