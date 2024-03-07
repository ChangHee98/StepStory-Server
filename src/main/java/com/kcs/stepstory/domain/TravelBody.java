package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "TravelBody")
public class TravelBody {
    @Id
    private long travelReportId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelBody that)) return false;
        return getReadPermission() == that.getReadPermission() && getTravelReport().equals(that.getTravelReport()) && getBody().equals(that.getBody()) && getCreatedAt().equals(that.getCreatedAt()) && getUpdatedAt().equals(that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTravelReport(), getBody(), getReadPermission(), getCreatedAt(), getUpdatedAt());
    }
}
