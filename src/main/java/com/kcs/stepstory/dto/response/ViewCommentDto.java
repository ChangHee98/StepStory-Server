package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Comment;
import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record ViewCommentDto(
        TravelReport travelReport,
        String content,
        Long parentCommentId,
        Timestamp createdAt
) {
    public static ViewCommentDto fromEntity(Comment comment){
        return ViewCommentDto.builder()
                .travelReport(comment.getTravelReport())
                .content(comment.getContent())
                .parentCommentId(comment.getParentCommentId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
