package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

@Builder
public record ModifyTravelReportFirstPageDto(
        ModifyTravelImageListDto modifyTravelImageListDto,
        String thumbnailUrl
) {
    public static ModifyTravelReportFirstPageDto fromEntity(
            ModifyTravelImageListDto modifyTravelImageListDto,
            TravelReport travelReport
    ){
        return ModifyTravelReportFirstPageDto.builder()
                .modifyTravelImageListDto(modifyTravelImageListDto)
                .thumbnailUrl(travelReport.getThumbnailUrl())
                .build();
    }
}
