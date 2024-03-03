package com.kcs.stepstory.controller;

import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.dto.common.ResponseDto;
import com.kcs.stepstory.dto.response.StepCountDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.type.ErrorCode;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.service.StepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;
    @GetMapping("/api/v1/step/main")
    public ResponseDto<StepCountForAllDto> getStepCountForall(
            @UserId Long userId
    ){
        return ResponseDto.ok(stepService.getStepCountForAll());
    }

    @GetMapping("/api/v1/step/main/{provinceId}")
    public ResponseDto<StepCountDto> getStepCountForProvince(
            @UserId Long userId,
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
                throw new CommonException(ErrorCode.BAD_REQUEST_PARAMETER);
        }
    }
}
