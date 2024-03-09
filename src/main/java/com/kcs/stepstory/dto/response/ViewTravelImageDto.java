package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.TravelImage;
import lombok.Builder;

@Builder
public record ViewTravelImageDto(
        Long travelImageId,
        String travelImageUrl,
        DetailCourse detailCourse
) {
    public static ViewTravelImageDto fromEntity(TravelImage travelImage){
        return ViewTravelImageDto.builder()
                .travelImageId(travelImage.getTravelImageId())
                .travelImageUrl(travelImage.getImageUrl())
                .detailCourse(travelImage.getDetailCourse())
                .build();
    }
}
