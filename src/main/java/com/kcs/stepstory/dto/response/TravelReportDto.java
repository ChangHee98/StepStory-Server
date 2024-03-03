package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

import java.sql.Timestamp;
@Builder
public record TravelReportDto(
        Long travelReportId,
        String title,
        String travelPeriod,
        String travelLocation,
        String thumbnailUrl,
        Timestamp createdAt,
        Timestamp updatedAt,
        Long wantToGoCount
) {
    public static TravelReportDto fromEntity(TravelReport travelReport) {
        return TravelReportDto.builder()
                .travelReportId(travelReport.getTravelReportId())
                .title(travelReport.getTitle())
                .travelPeriod(travelReport.getTravelPeriod())
                .travelLocation(travelReport.getTravelLocation())
                .thumbnailUrl(travelReport.getThumbnailUrl())
                .createdAt(travelReport.getCreatedAt())
                .updatedAt(travelReport.getUpdatedAt())
                .wantToGoCount(travelReport.getWantToGoCount())
                .build();
    }
}
