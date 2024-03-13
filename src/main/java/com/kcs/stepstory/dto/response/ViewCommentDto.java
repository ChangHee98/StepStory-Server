package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Comment;
import com.kcs.stepstory.domain.TravelReport;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record ViewCommentDto(
        Long travelReportId,
        String content,
        Long parentCommentId,
        Timestamp createdAt,
        String nickname,
        String profileImgUrl
) {
    public static ViewCommentDto fromEntity(Comment comment){
        return ViewCommentDto.builder()
                .travelReportId(comment.getTravelReport().getTravelReportId())
                .content(comment.getContent())
                .parentCommentId(comment.getParentCommentId())
                .createdAt(comment.getCreatedAt())
                .nickname(comment.getUser().getNickname())
                .profileImgUrl(comment.getUser().getProfileImgUrl())
                .build();
    }
}
