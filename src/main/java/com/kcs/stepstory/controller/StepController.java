package com.kcs.stepstory.controller;

import com.kcs.stepstory.dto.common.ResponseDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;
    @GetMapping("/api/v1/step/main")
    public ResponseDto<StepCountForAllDto> getStepCountForall(){
        return ResponseDto.ok(stepService.getStepCountForAll());
    }
}
