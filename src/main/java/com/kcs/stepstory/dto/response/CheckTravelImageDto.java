package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.TravelImage;
import lombok.Builder;


@Builder
public record CheckTravelImageDto(
        Long travelImageId,
        DetailCourse detailCourse,
        String imageUrl
){
    public static CheckTravelImageDto fromEntity(TravelImage travelImage){
        return CheckTravelImageDto.builder()
                .travelImageId(travelImage.getTravelImageId())
                .detailCourse(travelImage.getDetailCourse())
                .imageUrl(travelImage.getImageUrl())
                .build();
    }

    public static TravelImage toEntity(CheckTravelImageDto checkTravelImageDto){
        return TravelImage.builder()
                .travelImageId(checkTravelImageDto.travelImageId())
                .detailCourse(checkTravelImageDto.detailCourse)
                .imageUrl(checkTravelImageDto.imageUrl)
                .build();
    }
}
