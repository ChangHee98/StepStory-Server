package com.kcs.stepstory.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WantToGoId implements Serializable {
    private User user;
    private TravelReport travelReport;
}
