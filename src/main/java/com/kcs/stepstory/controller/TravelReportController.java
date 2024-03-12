package com.kcs.stepstory.controller;

import com.amazonaws.Response;
import com.kcs.stepstory.annotation.UserId;
import com.kcs.stepstory.domain.Comment;
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
@Tag(name = "TravelReport", description = "여행 기록 관련 API")
@RequiredArgsConstructor
public class TravelReportController {
    private final TravelReportService travelReportService;
    @GetMapping("/api/v1/no-auth/travel-report/{provinceId}")
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

    /*
     * Post_2에서 DetailCourse를 TravelImage에 넣어줄 때 사용
     * */
    @PatchMapping("/api/v1/users/travel-report/detail-course")
    public ResponseDto<PostTravelImageListDto> ReportImagesAndCourses(
            @UserId Long userId,
            @RequestBody PostTravelImageListDto postTravelImageListDto
            ){
        return ResponseDto.ok(travelReportService.updateImages(postTravelImageListDto));
    }

    /*
     * DetailCourse 추가
     * UI : Post_2
     *  */
    @PostMapping("/api/v1/users/travel-report/detail-course")
    public ResponseDto<AddDetailCourseDto> addDetailCourse(
            @UserId Long userId,
            @RequestBody AddDetailCourseDto addDetailCourseDto
    ){
        return ResponseDto.ok(travelReportService.addDetailCourse(addDetailCourseDto));
    }

    /*
       TravelReport와 연결된 TravelImages와 DetailCourses 그리고 thumnailUrl을 불러오는 api
       UI : Post_2, Post_3
     * */
    @GetMapping(value = {"/api/v1/users/travel-report/travel-image/{travelReportId}"})
    public ResponseDto<WriteTravelReportDto> getTravelImagesAndDetailCourses(
            @UserId Long userId,
            @PathVariable Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.getTravelImagesAndDetailCourses(travelReportId));
    }

    /*
     * 게시글 title, body, readPermission Update
     * UI : Post_3
     *  */
    @PatchMapping("/api/v1/users/travel-report")
    public ResponseDto<PostWriteTravelReportDto> writeFinalTravelReport(
            @UserId Long userId,
            @RequestBody PostWriteTravelReportDto postWriteTravelReportDto
    ){

        return ResponseDto.ok(travelReportService.updateFinalTravelReport(postWriteTravelReportDto));
    }

    /*
     * 게시글 상세보기
     * */
    @GetMapping("/api/v1/no-auth/travel-report/{travelReportId}")
    public ResponseDto<ViewTravelReportDto> viewTravelReport(
            @UserId Long userId,
            @PathVariable Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.getTravelReport(travelReportId));
    }

    /*
     * 게시글 상세 보기에서 WantToGo
     * 이미 WantToGo 눌렀으면 delete
     * 아니면 insert
     * */
    @PostMapping("/api/v1/users/travel-report/want-to-go")
    public ResponseDto<Long> pushWantToGoTravelReport(
            @UserId Long userId,
            @RequestBody Long travelReportId
    ){
        return ResponseDto.ok(travelReportService.pushWantToGo(userId, travelReportId));
    }

    /*
     * 게시글 삭제
     * */
    @DeleteMapping("/api/v1/users/travel-report/{travelReportId}")
    public ResponseDto<?> deleteTravelReport(
        @UserId Long userId,
        @PathVariable Long travelReportId
    ){
        travelReportService.deleteTravelReport(userId, travelReportId);

        return ResponseDto.ok(null);
    }

    /*
     * 게시글 수정 시 TravelImages, DetailCourses, thumnailUrl을 가져오는 api
     *  */
    @GetMapping("/api/v1/users/travel-report/my/travel-image/{travelReportId}")
    public ResponseDto<ModifyTravelReportFirstPageDto> viewModifyReportFirstPage(
            @UserId Long userId,
            @PathVariable Long travelReportId
    ){
            return ResponseDto.ok(travelReportService.modifyTravelReportFirst(travelReportId, userId));
    }

    /*
     * 댓글 조회
     *  */
    @GetMapping("/api/v1/users/travel-report/comment/{travelReportId}")
    public ResponseDto<ViewCommentListDto> viewComments(
            @UserId Long userId,
            @PathVariable Long travelReportId
    ) {
        return ResponseDto.ok(travelReportService.viewCommentList(travelReportId));
    }

    /*
     * 댓글 작성
     * */
    @PostMapping("/api/v1/users/travel-report/comment")
    public ResponseDto<Comment> writeComments(
            @UserId Long userId,
            @RequestBody WriteCommentDto writeCommentDto
    ) {
        return ResponseDto.created(travelReportService.writeComment(writeCommentDto, userId));

    }

    /*
     * 댓글 업데이트
     * */
    @PatchMapping("/api/v1/users/travel-report/comment")
    public ResponseDto<?> updateComment(
            @UserId Long userId,
            @RequestBody UpdateCommentDto updateCommentDto
    ) {
        travelReportService.updateComment(updateCommentDto, userId);
        return ResponseDto.ok(null);
    }

    @DeleteMapping("/api/v1/users/travel-report/comment/{commentId}")
    public ResponseDto<?> deleteComment(
            @UserId Long userId,
            @PathVariable Long commentId
    ){
        travelReportService.deleteComment(commentId, userId);
        return ResponseDto.ok(null);
    }
}
