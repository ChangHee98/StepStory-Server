package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Step;
import lombok.Builder;

@Builder
public record StepCountForSeoulDto(
        Integer gangnam,
        Integer gangdong,
        Integer gangbuk,
        Integer gangseo,
        Integer gwanak,
        Integer gwangjin,
        Integer guro,
        Integer geumcheon,
        Integer nowon,
        Integer dobong,
        Integer dongdaemun,
        Integer dongjak,
        Integer mapo,
        Integer seodaemun,
        Integer seocho,
        Integer seongdong,
        Integer seongbuk,
        Integer songpa,
        Integer yangcheon,
        Integer yeongdeungpo,
        Integer yongsan,
        Integer eunpyeong,
        Integer jongno,
        Integer jung,
        Integer jungnang
) implements StepCountDto{



}

