package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.TravelImage;
import com.kcs.stepstory.domain.TravelImageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelImageRepository extends JpaRepository<TravelImage, TravelImageId> {
}
