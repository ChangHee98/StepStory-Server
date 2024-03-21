package com.kcs.stepstory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "DetailCourse")
public class DetailCourse {

    @Id
    @Column(name = "detailCourseId",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailCourseId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReportId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelReport travelReport;

    @Column(name = "travelDate",nullable = true)
    private Timestamp travelDate;
    @Column(name = "latitude",nullable = true)
    private Double latitude;
    @Column(name = "longitude",nullable = true)
    private Double longitude;
    @Column(name = "sequence",nullable = true)
    private Integer sequence;
    @Column(name = "locationName",nullable = true)
    private String locationName;

    @Builder
    public DetailCourse(TravelReport travelReport, Timestamp travelDate, Double latitude, Double longitude, Integer sequence, String locationName){
        this.travelReport = travelReport;
        this.travelDate = travelDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sequence = sequence;
        this.locationName = locationName;
    }

    public void updateDetailCourse(Integer sequence, String locationName){
        this.sequence = sequence;
        this.locationName = locationName;
    }

    public void updateSequence(Integer sequence){
        this.sequence = sequence;
    }
}
