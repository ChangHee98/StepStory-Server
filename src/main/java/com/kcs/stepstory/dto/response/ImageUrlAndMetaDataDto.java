package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record ImageUrlAndMetaDataDto(
        String imageUrl,
        Timestamp travelDate,
        Double latitude,
        Double longitude
) {

}
