package com.kcs.stepstory.repository;

import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForSeoulDto;

public interface StepRepositoryCustom {
    StepCountForAllDto countStepsForAllRegions();
    StepCountForSeoulDto countStepsForSeoul();
}
