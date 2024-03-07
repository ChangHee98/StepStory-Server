package com.kcs.stepstory.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentId implements Serializable {
    private Long commentId;
    private TravelReport travelReport;
}
