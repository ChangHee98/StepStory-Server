package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.sql.Timestamp;
@Builder
public record DetailCourseDto(
        Timestamp travelDate,
        Double latitude,
        Double longitude,
        String locationName,
        Integer sequence
) {
}