package com.kcs.stepstory.dto.request;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.TravelImage;
import com.kcs.stepstory.dto.response.CheckTravelImageDto;
import lombok.Builder;


@Builder
public record PostTravelImageDto(
        Long travelImageId,
        Long detailCourseId,
        int sequence,
        String locationName
){
    public static PostTravelImageDto fromEntity(TravelImage travelImage){
        return PostTravelImageDto.builder()
                .travelImageId(travelImage.getTravelImageId())
                .detailCourseId(travelImage.getDetailCourse().getDetailCourseId())
                .sequence(travelImage.getDetailCourse().getSequence())
                .locationName(travelImage.getDetailCourse().getLocationName())
                .build();
    }
}
