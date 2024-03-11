package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ModifyTravelImageListDto(
        List<ModifyTravelImageDto> modifyTravelImageDtos
) {
    public static ModifyTravelImageListDto fromEntity(List<ModifyTravelImageDto> modifyTravelImageDtos){
        return new ModifyTravelImageListDto(modifyTravelImageDtos);
    }
}
