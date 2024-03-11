package com.kcs.stepstory.dto.response;

import java.util.List;

public record CheckTravelImageListDto(
        List<CheckTravelImageDto> checkTravelImageDtoList
) {
    public static CheckTravelImageListDto fromEntity(List<CheckTravelImageDto> checkTravelImageDtoList){
        return new CheckTravelImageListDto(checkTravelImageDtoList);
    }
}
