package com.kcs.stepstory.dto.response;


import com.kcs.stepstory.domain.TravelBody;
import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

import java.sql.Timestamp;
@Builder
public record ViewTravelReportDto(
        ViewTravelImageListDto viewTravelImageListDto,
        Long travelReportId,
        CommonUserSourseDto commonUserSourseDto,
        String title,
        String thumbnailUrl,
        Timestamp createdAt,
        Timestamp updatedAt,
        Long wantToGoCount,
        String body
) {
    public static ViewTravelReportDto fromEntity(ViewTravelImageListDto viewTravelImageListDto, CommonUserSourseDto commonUserSourseDto, TravelReport travelReport, TravelBody travelBody){
        return ViewTravelReportDto.builder()
                .viewTravelImageListDto(viewTravelImageListDto)
                .travelReportId(travelReport.getTravelReportId())
                .commonUserSourseDto(commonUserSourseDto)
                .title(travelReport.getTitle())
                .thumbnailUrl(travelReport.getThumbnailUrl())
                .createdAt(travelReport.getCreatedAt())
                .updatedAt(travelReport.getUpdatedAt())
                .wantToGoCount(travelReport.getWantToGoCount())
                .body(travelBody.getBody())
                .build();
    }
}
