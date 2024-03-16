package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UploadImageMetaDataDto(
        List<String> gps,
        String startDay,
        String endDay
) {

}
