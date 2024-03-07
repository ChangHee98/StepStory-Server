package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@IdClass(TravelImageId.class)
@Table(name = "TravelImage")
public class TravelImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travelImageId",nullable = false)
    private Long travelImageId;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReportId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelReport travelReport;

    @Column(name = "imageUrl",nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "detailCourseId", nullable = false),
        @JoinColumn(name = "travelReportId", nullable = false)
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DetailCourse detailCourse;

    @Builder
    public TravelImage(TravelReport travelReport, String imageUrl, DetailCourse detailCourse){
        this.travelReport = travelReport;
        this.imageUrl = imageUrl;
        this.detailCourse = detailCourse;
    }



}
