package com.kcs.stepstory.repository;

import com.kcs.stepstory.dto.response.StepCountForAllDto;

public interface StepRepositoryCustom {
    StepCountForAllDto countStepsForAllRegions();
}
