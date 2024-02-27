package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.TravelBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelBodyRepository extends JpaRepository<TravelBody, Long> {
}
