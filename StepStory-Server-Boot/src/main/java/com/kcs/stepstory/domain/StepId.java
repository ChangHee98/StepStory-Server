package com.kcs.stepstory.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StepId implements Serializable {
    private Long stepId;
    private TravelReport travelReport;
}
