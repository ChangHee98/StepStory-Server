package com.kcs.stepstory.dto.response;

import lombok.Builder;

@Builder
public record StepDto(
        String province,
        String city,
        String district
) {
}