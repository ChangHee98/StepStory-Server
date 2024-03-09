package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "TravelReport")
public class TravelReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travelReportId",nullable = false)
    private Long travelReportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "travelPeriod",nullable = false)
    private String travelPeriod;
    @Column(name = "travelLocation",nullable = false)
    private String travelLocation;
    @Column(name = "thumbnailUrl",nullable = false)
    private String thumbnailUrl;
    @CreationTimestamp
    @Column(name = "createdAt",nullable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updatedAt",nullable = true)
    private Timestamp updatedAt;
    @Column(name = "wantToGoCount",nullable = false)
    private Long wantToGoCount;

    @Builder
    public TravelReport(User user, String title, String travelPeriod, String travelLocation, String thumbnailUrl){
        this.user = user;
        this.title = title;
        this.travelPeriod = travelPeriod;
        this.travelLocation = travelLocation;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.wantToGoCount = 0L;
    }

    public void updateTravelReportTitle(String title){
        this.title = title;
    }

    public void updateTravelReportWantToGoCount(Long wantToGoCount){
        this.wantToGoCount = wantToGoCount;
    }
}
