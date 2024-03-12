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

    @Column(name = "travelDate",nullable = false)
    private Timestamp travelDate;
    @Column(name = "gps",nullable = false)
    private String gps;
    @Column(name = "sequence",nullable = false)
    private int sequence;
    @Column(name = "locationName",nullable = true)
    private String locationName;

    @Builder
    public DetailCourse(TravelReport travelReport, Timestamp travelDate, String gps, int sequence, String locationName){
        this.travelReport = travelReport;
        this.travelDate = travelDate;
        this.gps = gps;
        this.sequence = sequence;
        this.locationName = locationName;
    }

    public void updateDetailCourse(Timestamp travelDate, String gps, int sequence, String locationName){
        this.travelDate = travelDate;
        this.gps = gps;
        this.sequence = sequence;
        this.locationName = locationName;
    }
}
