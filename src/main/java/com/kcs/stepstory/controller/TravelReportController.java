package com.kcs.stepstory.controller;

import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.dto.common.ResponseDto;
import com.kcs.stepstory.dto.response.TravelReportListDto;
import com.kcs.stepstory.dto.type.ErrorCode;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.service.TravelReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelReportController {
    private final TravelReportService travelReportService;
    @GetMapping("/api/v1/travel-report/{provinceId}")
    public ResponseDto<TravelReportListDto> getTravelReportList(
            @UserId Long userId,
            @PathVariable("provinceId") Long provinceId,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "disctrict", required = false) String district
    ){
        String province = "";
        switch (provinceId.intValue()){
            case 1:
                province = "seoul";
                break;
            case 2:
                province = "busan";
                break;
            case 9:
                province = "gyeonggi";
                break;
            default:
                throw new CommonException(ErrorCode.BAD_REQUEST_PARAMETER);
        }
        return ResponseDto.ok(travelReportService.getTravelReportList(province, city, district));
    }

}
