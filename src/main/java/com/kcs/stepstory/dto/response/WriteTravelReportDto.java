package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

@Builder
public record WriteTravelReportDto(
        WriteReportTravelImageListDto writeReportTravelImageListDto,
        String thumbnailUrl
) {

}
