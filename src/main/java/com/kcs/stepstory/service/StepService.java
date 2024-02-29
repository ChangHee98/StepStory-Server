package com.kcs.stepstory.service;

import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForSeoulDto;
import com.kcs.stepstory.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;

    public StepCountForAllDto getStepCountForAll(){
        return stepRepository.countStepsForAllRegions();
    }
    public StepCountForSeoulDto getStepCountForSeoul(){
        return stepRepository.countStepsForSeoul();
    }
}
