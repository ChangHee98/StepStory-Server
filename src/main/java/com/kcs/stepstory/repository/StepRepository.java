package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Step;
import com.kcs.stepstory.domain.StepId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Step, StepId>, StepRepositoryCustom{
}
