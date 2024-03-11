package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.TravelImage;
import lombok.Builder;

@Builder
public record ModifyTravelImageDto(
        Long travelImageId,
        String travelImageUrl,
        DetailCourse detailCourse
) {
    public static ModifyTravelImageDto fromEntity(TravelImage travelImage){
        return  ModifyTravelImageDto.builder()
                .travelImageId(travelImage.getTravelImageId())
                .travelImageUrl(travelImage.getImageUrl())
                .detailCourse(travelImage.getDetailCourse())
                .build();
    }

}
