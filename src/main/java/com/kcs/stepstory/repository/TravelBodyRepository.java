package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.TravelBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelBodyRepository extends JpaRepository<TravelBody, Long> {
    List<TravelBody> findTravelBodiesByTravelReportIdInAndReadPermissionEquals(List<Long> travelReportIds, int readPermission);

    List<TravelBody> findTravelBodiesByTravelReportIdIn(List<Long> travelReportIds);

    List<TravelBody> findTravelBodiesByTravelReportIdInAndReadPermissionIsIn(List<Long> travelReportIds, int[] readPermission);
}
