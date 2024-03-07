package com.kcs.stepstory.dto.response;

import lombok.Builder;

@Builder
public record StepCountForGyeonggiDto(
        Integer suwon,
        Integer seongnam,
        Integer yongin,
        Integer anyang,
        Integer ansan,
        Integer gwacheon,
        Integer gwangmyeong,
        Integer gwangju,
        Integer gunpo,
        Integer bucheon,
        Integer siheung,
        Integer gimpo,
        Integer anseong,
        Integer osan,
        Integer uiwang,
        Integer icheon,
        Integer pyeongtaek,
        Integer hanam,
        Integer hwaseong,
        Integer yeoju,
        Integer yangpyeong,
        Integer goyang,
        Integer guri,
        Integer namyangju,
        Integer dongducheon,
        Integer yangju,
        Integer uijeongbu,
        Integer paju,
        Integer pocheon,
        Integer yeoncheon,
        Integer gapyeong
) implements StepCountDto{
}

