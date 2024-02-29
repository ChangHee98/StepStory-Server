package com.kcs.stepstory.dto.response;

import lombok.Builder;
@Builder
public record StepCountForAllDto(
    Integer seoul,
    Integer busan,
    Integer incheon,
    Integer gwanju,
    Integer ulsan,
    Integer sejong,
    Integer gyeonggi,
    Integer gangwon,
    Integer northChungcheong,
    Integer southChungcheong,
    Integer northJeolla,
    Integer southJeolla,
    Integer northGyeongsang,
    Integer southGyeongsang,
    Integer jeju
) {
    public static StepCountForAllDto empty() {
        return StepCountForAllDto.builder()
                .seoul(0)
                .busan(0)
                .incheon(0)
                .gwanju(0)
                .ulsan(0)
                .sejong(0)
                .gyeonggi(0)
                .gangwon(0)
                .northChungcheong(0)
                .southChungcheong(0)
                .northJeolla(0)
                .southJeolla(0)
                .northGyeongsang(0)
                .southGyeongsang(0)
                .jeju(0)
                .build();
    }
}
