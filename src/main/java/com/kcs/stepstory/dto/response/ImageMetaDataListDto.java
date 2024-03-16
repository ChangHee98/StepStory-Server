package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ImageMetaDataListDto(
        List<ImageMetaDataDto> imageMetaDataDtos,
        String startDay,
        String endDay
) {

}
