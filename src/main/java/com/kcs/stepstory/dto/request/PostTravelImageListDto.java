package com.kcs.stepstory.dto.request;

import com.kcs.stepstory.dto.response.CheckTravelImageDto;
import com.kcs.stepstory.dto.response.CheckTravelImageListDto;

import java.util.List;

public record PostTravelImageListDto(
        List<PostTravelImageDto> postTravelImageDtoList
) {
    public static PostTravelImageListDto fromEntity(List<PostTravelImageDto> postTravelImageDtoList){
        return new PostTravelImageListDto(postTravelImageDtoList);
    }
}
