package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Step;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public record StepCountForAllDto (
        Integer seoul,
        Integer busan,
        Integer incheon,
        Integer gwanju,
        Integer ulsan,
        Integer sejong,
        Integer gyeonggi,
        Integer gangwon,
        Integer chungbuk,
        Integer chungnam,
        Integer jeonbuk,
        Integer jeonnam,
        Integer gyeongbuk,
        Integer gyeongnam,
        Integer jeju
) {



}
