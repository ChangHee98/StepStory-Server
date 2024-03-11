package com.kcs.stepstory.dto.request;

public record UpdateCommentDto(
        Long commentId,
        String content
) {

}
