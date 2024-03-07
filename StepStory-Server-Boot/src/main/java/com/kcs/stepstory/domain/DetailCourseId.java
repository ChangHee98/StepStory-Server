package com.kcs.stepstory.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DetailCourseId implements Serializable {
    private Long detailCourseId;
    private TravelReport travelReport;
}
