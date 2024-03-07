package com.kcs.stepstory.dto.response;

import lombok.Builder;

@Builder
public record StepCountForBusanDto(

        Integer jung,
        Integer dong,
        Integer seo,
        Integer nam,
        Integer buk,
        Integer yeongdo,
        Integer busanjin,
        Integer dongrae,
        Integer haeundae,
        Integer saha,
        Integer geumjeong,
        Integer gangseo,
        Integer yeonje,
        Integer suyeong,
        Integer sasang,
        Integer gijang
)implements StepCountDto{
}
