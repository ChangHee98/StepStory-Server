package com.kcs.stepstory.dto.request;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.TravelImage;
import com.kcs.stepstory.dto.response.CheckTravelImageDto;
import lombok.Builder;


@Builder
public record PostTravelImageDto(
        Long travelImageId,
        DetailCourse detailCourse,
        String imageUrl
){
    public static PostTravelImageDto fromEntity(TravelImage travelImage){
        return PostTravelImageDto.builder()
                .travelImageId(travelImage.getTravelImageId())
                .detailCourse(travelImage.getDetailCourse())
                .imageUrl(travelImage.getImageUrl())
                .build();
    }

    public static TravelImage toEntity(PostTravelImageDto postTravelImageDto){
        return TravelImage.builder()
                .travelImageId(postTravelImageDto.travelImageId)
                .detailCourse(postTravelImageDto.detailCourse)
                .imageUrl(postTravelImageDto.imageUrl)
                .build();
    }
}
