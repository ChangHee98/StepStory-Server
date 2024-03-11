package com.kcs.stepstory.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ViewCommentListDto(
        List<ViewCommentDto> viewCommentDtos
) {
    public static ViewCommentListDto fromEntity(List<ViewCommentDto> viewCommentDtos){
        return new ViewCommentListDto(viewCommentDtos);
    }
}
