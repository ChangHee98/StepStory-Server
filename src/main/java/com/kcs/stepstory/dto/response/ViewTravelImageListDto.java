package com.kcs.stepstory.dto.response;

import java.util.List;

public record ViewTravelImageListDto(
        List<ViewTravelImageDto> viewTravelImageDtos
) {
    public static ViewTravelImageListDto fromEntity(List<ViewTravelImageDto> viewTravelImageDtos){
        return new ViewTravelImageListDto(viewTravelImageDtos);
    }
}
