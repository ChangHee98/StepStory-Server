package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "TravelBody")
public class TravelBody {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReportId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelReport travelReport;

    @Column(name= "body",nullable = false)
    private String body;
    @Column(name= "readPermission",nullable = false)
    private int readPermission;
    @CreationTimestamp
    @Column(name= "createdAt",nullable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name= "updatedAt",nullable = true)
    private Timestamp updatedAt;

    @Builder
    public TravelBody(TravelReport travelReport, String body, int readPermission){
        this.travelReport = travelReport;
        this.body = body;
        this.readPermission = readPermission;
        this.createdAt = new Timestamp(System.currentTimeMillis());

    }
}
