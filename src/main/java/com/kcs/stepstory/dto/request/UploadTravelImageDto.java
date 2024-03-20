package com.kcs.stepstory.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UploadTravelImageDto(
        @JsonProperty("thumbnail_index")
        Integer thumbnailIndex
) {
}