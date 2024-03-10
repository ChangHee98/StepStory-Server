package com.kcs.stepstory.controller;

import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.global.ResponseDto;
import com.kcs.stepstory.dto.request.*;
import com.kcs.stepstory.dto.response.*;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.exception.ErrorCode;
import com.kcs.stepstory.intercepter.pre.UserIdArgumentResolver;
import com.kcs.stepstory.service.TravelReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    @PatchMapping(value = {"/api/v1/users/travel-report/insert", "/api/v1/users/travel-report/temporary"})
    public Map<String, String> writeFinalTravelReport(
            @UserId Long userId,
            @RequestBody PostWriteTravelReportDto postWriteTravelReportDto,
            HttpServletRequest request
    ){
        travelReportService.updateFinalTravelReport(postWriteTravelReportDto);

        Map<String, String> response = new HashMap<>();
        String requestUri = request.getRequestURI();

        if(requestUri.equals("/api/v1/users/travel-report/insert")){
            response.put("message", "게시글 작성을 완료했습니다.");
        }else if(requestUri.equals("/api/v1/users/travel-report/temporary")) {
            response.put("message", "게시글을 임시 저장했습니다.");
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_END_POINT);
        }

        return response;
    }

    @GetMapping("/api/v1/no-auth/travel-report/{provinceId}/")
    public ResponseDto<ViewTravelReportDto> viewTravelReport(
            @UserId Long userId,
            @RequestParam Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.getTravelReport(travelReportId));
    }

    @PostMapping("/api/v1/users/travel-report/want-to-go")
    public ResponseDto<Long> pushWantToGoTravelReport(
            @UserId Long userId,
            @RequestParam Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.pushWantToGo(userId, travelReportId));
    }

    @DeleteMapping("/api/v1/users/travel-report")
    public Map<String, Long> deleteTravelReport(
        @UserId Long userId,
        @RequestParam Long travelReportId
    ){

        Map<String, Long> response = new HashMap<>();
        response.put("travelReportId", travelReportService.deleteTravelReport(userId, travelReportId));

        return response;
    }

    @GetMapping("/api/v1/users/travel-report/comment")
    public ResponseDto<ViewCommentListDto> viewComments(
            @UserId Long userId,
            @RequestParam Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.viewCommentList(travelReportId));
    }

    @PostMapping("/api/v1/users/travel-report/comment")
    public Map<String, String> writeComments(
            @UserId Long userId,
            @RequestBody WriteCommentDto writeCommentDto
    ){
        travelReportService.writeComment(writeCommentDto, userId);
        Map<String, String> response = new HashMap<>();
        response.put("message","댓글 작성이 완료되었습니다.");
        return response;
    }

    @PatchMapping("/api/v1/users/travel-report/comment")
    public Map<String, String> updateComment(
            @UserId Long userId,
            @RequestBody UpdateCommentDto updateCommentDto
    ){

        travelReportService.updateComment(updateCommentDto, userId);
        Map<String, String> response = new HashMap<>();
        response.put("message","댓글 수정이 완료되었습니다.");

        return response;
    }
}
