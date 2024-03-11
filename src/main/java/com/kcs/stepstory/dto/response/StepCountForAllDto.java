package com.kcs.stepstory.dto.response;

import lombok.Builder;
@Builder
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
