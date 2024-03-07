package com.kcs.stepstory.dto.response;

import java.util.List;

public record TravelReportListDto(
        List<TravelReportDto> myTravelReportList
) {
    public static TravelReportListDto fromEntity(List<TravelReportDto> myTravelReportList) {
        return new TravelReportListDto(myTravelReportList);
    }
}
