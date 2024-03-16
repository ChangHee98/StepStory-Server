package com.kcs.stepstory.controller;

import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.dto.global.ResponseDto;
import com.kcs.stepstory.dto.response.StepCountDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForSeoulDto;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.service.MyStepService;
import com.kcs.stepstory.service.StepService;
import com.kcs.stepstory.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "Step", description = "여행 기록 발자국 관련 API")
@RequiredArgsConstructor
public class StepController {
    private final StepService stepService;
    private final MyStepService myStepService;
    @GetMapping("/no-auth/step/main")
    @Operation(summary = "전국 지역별 Step 개수 조회", description = "전국 지역별 Step 개수를 조회합니다.")
    public ResponseDto<StepCountForAllDto> getStepCountForall(){
        return ResponseDto.ok(stepService.getStepCountForAll());
    }

    @GetMapping("/no-auth/step/main/{provinceId}")
    @Operation(summary = "특정 지역별 Step 개수 조회", description = "특정 지역별 Step 개수를 조회합니다.")
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

    /**
     *
     * MyStoryPage 발자국 조회 - 전체 기능
     * /api/v1/users/step/mystory
     */
    @GetMapping("users/step/my/main")
    public ResponseDto<StepCountForAllDto> getMyStepCountForAll(@UserId Long userId) {
        return ResponseDto.ok(myStepService.getMyStepCountForAll(userId));
    }

    /**
     *
     * MyStoryPage 발자국 조회 - 서울 기능
     */
//    @GetMapping("users/step/my/{provinceName}")
//    public ResponseDto<StepCountForSeoulDto> getMyStepCountForSeoul(@UserId Long userId, @PathVariable String provinceName) {
//        if (provinceName.equals("Seoul")) {
//            return ResponseDto.ok(myStepService.getMyStepCountForSeoul(userId, provinceName));
//        } else if (provinceName.equals("Gyeonggi")) {
//            return ResponseDto.ok(myStepService.getMyStepCountForGyeonggi(userId, provinceName));
//        } else if (provinceName.equals("Busan")) {
//            return null;
//        } else {
//            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
//        }
//    }

    @GetMapping("users/step/my/{provinceName}")
    public ResponseEntity<ResponseDto<?>> getMyStepCount(@UserId Long userId, @PathVariable String provinceName) {
        ResponseDto<?> responseDto;

        if (provinceName.equals("Seoul")) {
            responseDto = ResponseDto.ok(myStepService.getMyStepCountForSeoul(userId, provinceName));
        } else if (provinceName.equals("Gyeonggi")) {
            responseDto = ResponseDto.ok(myStepService.getMyStepCountForGyeonggi(userId, provinceName));
        } else if (provinceName.equals("Busan")) {
            // Add logic for Busan if needed
            responseDto = null; // Placeholder
        } else {
            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
        return ResponseEntity.ok(responseDto);
    }








}
