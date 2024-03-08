package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.TravelImage;
import com.kcs.stepstory.domain.TravelImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelImageRepository extends JpaRepository<TravelImage, TravelImageId> {
    public List<TravelImage> getTravelImagesByTravelReport(Long travelReportId);

    public void updateTravelImageByTravelImageId(Long travelImageId);

    public void updateTravelImageByImageUrlIs(Long detailCouseId);
    public void updateTravelImagesByTravelImageId(List<TravelImage> travelImageList);

    TravelImage findById(Long travelImageId);
}
