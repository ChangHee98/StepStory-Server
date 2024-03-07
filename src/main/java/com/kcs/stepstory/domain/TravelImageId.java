package com.kcs.stepstory.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TravelImageId implements Serializable {
    private Long travelImageId;
    private TravelReport travelReport;

}
