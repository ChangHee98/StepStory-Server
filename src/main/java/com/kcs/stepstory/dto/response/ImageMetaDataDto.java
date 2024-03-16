package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record ImageMetaDataDto(
        String gps,
        Timestamp travelDate
) {

}
