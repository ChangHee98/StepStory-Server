package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.TravelImage;
import com.kcs.stepstory.domain.TravelImageId;
import com.kcs.stepstory.domain.TravelReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelImageRepository extends JpaRepository<TravelImage, TravelImageId> {
    public List<TravelImage> getTravelImagesByTravelReport(TravelReport travelReport);

    TravelImage findByTravelImageId(Long travelImageId);
}
