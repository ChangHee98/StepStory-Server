package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.TravelReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelReportRepository extends JpaRepository<TravelReport, Long> {
}
