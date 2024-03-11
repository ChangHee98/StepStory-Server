package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ViewTravelImageListDto(
        List<ViewTravelImageDto> viewTravelImageDtos
) {

}
