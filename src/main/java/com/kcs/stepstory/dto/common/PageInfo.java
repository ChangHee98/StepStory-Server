package com.kcs.stepstory.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageInfo(
        @JsonProperty("current_page") Integer currentPage,
        @JsonProperty("current_size") Integer size,
        @JsonProperty("total_page") Integer totalPage,
        @JsonProperty("total_element") Integer totalElement) {

    public static PageInfo fromPage(Page page) {
        return PageInfo.builder()
                .currentPage(page.getNumber() + 1)
                .totalPage(page.getTotalPages())
                .totalElement((int) page.getTotalElements())
                .size(page.getSize())
                .build();
    }
}
