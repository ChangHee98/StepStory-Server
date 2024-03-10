package com.kcs.stepstory.dto.request;

import com.kcs.stepstory.domain.Comment;
import com.kcs.stepstory.domain.TravelReport;
import com.kcs.stepstory.domain.User;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record WriteCommentDto(
        TravelReport travelReport,
        String content,
        Long parentCommentId,
        Timestamp createdAt,
        User user
) {

}
