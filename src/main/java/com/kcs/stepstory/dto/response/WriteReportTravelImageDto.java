package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.TravelImage;
import lombok.Builder;

@Builder
public record WriteReportTravelImageDto(
        Long travelImageId,
        String travelImageUrl,
        DetailCourse detailCourse
) {
    public static WriteReportTravelImageDto fromEntity(TravelImage travelImage){
        return WriteReportTravelImageDto.builder()
                .travelImageId(travelImage.getTravelImageId())
                .travelImageUrl(travelImage.getImageUrl())
                .detailCourse(travelImage.getDetailCourse())
                .build();
    }
}
