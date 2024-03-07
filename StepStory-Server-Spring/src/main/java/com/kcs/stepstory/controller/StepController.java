package com.kcs.stepstory.controller;

import com.kcs.stepstory.dto.global.ResponseDto;
import com.kcs.stepstory.dto.response.StepCountDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.service.StepService;
import com.kcs.stepstory.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/no-auth/step")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;
    @GetMapping("/main")
    public ResponseDto<StepCountForAllDto> getStepCountForall(){
        return ResponseDto.ok(stepService.getStepCountForAll());
    }

    @GetMapping("/main/{provinceId}")
    public ResponseDto<StepCountDto> getStepCountForProvince(
            @PathVariable("provinceId") Long provinceId
    ){
        switch (provinceId.intValue()){
            case 1:
                return ResponseDto.ok(stepService.getStepCountForSeoul());
            case 2:
                return ResponseDto.ok(stepService.getStepCountForBusan());
//            case 3:
//                return ResponseDto.ok(stepService.getStepCountForDaegu());
//            case 4:
//                return ResponseDto.ok(stepService.getStepCountForIncheon());
//            case 5:
//                return ResponseDto.ok(stepService.getStepCountForGwangju());
//            case 6:
//                return ResponseDto.ok(stepService.getStepCountForDaejeon());
//            case 7:
//                return ResponseDto.ok(stepService.getStepCountForUlsan());
//            case 8:
//                return ResponseDto.ok(stepService.getStepCountForSejong());
            case 9:
                return ResponseDto.ok(stepService.getStepCountForGyeonggi());
//            case 10:
//                return ResponseDto.ok(stepService.getStepCountForGangwon());
//            case 11:
//                return ResponseDto.ok(stepService.getStepCountForChungbuk());
//            case 12:
//                return ResponseDto.ok(stepService.getStepCountForChungnam());
//            case 13:
//                return ResponseDto.ok(stepService.getStepCountForJeonbuk());
//            case 14:
//                return ResponseDto.ok(stepService.getStepCountForJeonnam());
//            case 15:
//                return ResponseDto.ok(stepService.getStepCountForGyeongbuk());
//            case 16:
//                return ResponseDto.ok(stepService.getStepCountForGyeongnam());
//            case 17:
//                return ResponseDto.ok(stepService.getStepCountForJeju());
            default:
                throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
    }
}
