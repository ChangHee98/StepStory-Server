package com.kcs.stepstory.dto.request;

import com.kcs.stepstory.domain.TravelBody;
import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

@Builder
public record PostWriteTravelReportDto(
        String title,
        String body,
        int readPermission,
        Long travelReportId
) {
    public static PostWriteTravelReportDto fromEntity(
            TravelReport travelReport,
            TravelBody travelBody
            ){
        return PostWriteTravelReportDto.builder()
                .title(travelReport.getTitle())
                .body(travelBody.getBody())
                .readPermission(travelBody.getReadPermission())
                .travelReportId(travelReport.getTravelReportId())
                .build();
    }


}
