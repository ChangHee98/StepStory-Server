package com.kcs.stepstory.controller;

import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.global.ResponseDto;
import com.kcs.stepstory.dto.request.AddDetailCourseDto;
import com.kcs.stepstory.dto.request.PostTravelImageListDto;
import com.kcs.stepstory.dto.request.PostWriteTravelReportDto;
import com.kcs.stepstory.dto.response.CheckTravelImageListDto;
import com.kcs.stepstory.dto.response.TravelReportListDto;
import com.kcs.stepstory.dto.response.ViewTravelReportDto;
import com.kcs.stepstory.dto.response.WriteReportTravelImageListDto;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.exception.ErrorCode;
import com.kcs.stepstory.intercepter.pre.UserIdArgumentResolver;
import com.kcs.stepstory.service.TravelReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/no-auth/travel-report")
@Tag(name = "TravelReport", description = "여행 기록 관련 API")
@RequiredArgsConstructor
public class TravelReportController {
    private final TravelReportService travelReportService;
    @GetMapping("/{provinceId}")
    @Operation(summary = "여행 기록 목록 조회", description = "특정 지역의 여행 기록 목록을 조회합니다.")
    public ResponseDto<TravelReportListDto> getTravelReportList(
            @PathVariable("provinceId") Long provinceId,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "disctrict", required = false) String district
    ){
        String province;
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

    @GetMapping("/api/v1/travel-report/detail-course/view-check/{travelReportId}")
    public ResponseDto<CheckTravelImageListDto> viewReportImagesAndCourses(
            @UserId Long userId,
            @RequestParam Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.getCheckTravelImageList(travelReportId));
    }

    @PatchMapping("/api/v1/users/travel-report/detail-course")
    public ResponseDto<PostTravelImageListDto> ReportImagesAndCourses(
            @UserId Long userId,
            @RequestBody PostTravelImageListDto postTravelImageListDto
            ){
        return ResponseDto.ok(travelReportService.updateImages(postTravelImageListDto));
    }

    @PostMapping("/api/v1/users/travel-report/detail-course/location")
    public Map<String, String> addDetailCourse(
            @UserId Long userId,
            @RequestBody AddDetailCourseDto addDetailCourseDto
    ){
        travelReportService.addDetailCourse(addDetailCourseDto);
        Map<String, String> response = new HashMap<>();
        response.put("message","코스가 추가되었습니다.");

        return response;
    }

    @GetMapping("/api/v1/users/travel-report/view-insert")
    public ResponseDto<WriteReportTravelImageListDto> viewWriteTravelReportPage(
            @UserId Long userId,
            @RequestParam Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.getWriteTravelImageList(travelReportId));
    }

    @PatchMapping("/api/v1/users/travel-report/insert")
    public Map<String, String> writeFinalTravelReport(
            @UserId Long userId,
            @RequestBody PostWriteTravelReportDto postWriteTravelReportDto
    ){
        travelReportService.updateFinalTravelReport(postWriteTravelReportDto);


        Map<String, String> response = new HashMap<>();
        response.put("message", "게시글 작성을 완료했습니다.");
        return response;
    }

    @GetMapping("/api/v1/no-auth/travel-report/{provinceId}/")
    public ViewTravelReportDto viewTravelReport(
            @UserId Long userId,
            @RequestParam Long travelReportId
    ){
        return travelReportService.getTravelReport(travelReportId);
    }

}
