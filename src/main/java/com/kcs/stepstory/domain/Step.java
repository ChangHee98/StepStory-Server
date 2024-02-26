package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@DynamicUpdate
@IdClass(StepId.class)
@Table(name = "Step")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stepId", nullable = false)
    private Long stepId;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travelReportId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelReport travelReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "province" ,nullable = false)
    private String province;
    @Column(name = "city" ,nullable = false)
    private String city;
    @Column(name = "district" ,nullable = true)
    private String district;

    @Builder
    public Step(TravelReport travelReport, User user, String province, String city, String district){
        this.travelReport = travelReport;
        this.user = user;
        this.province = province;
        this.city = city;
        this.district = district;
    }

}
