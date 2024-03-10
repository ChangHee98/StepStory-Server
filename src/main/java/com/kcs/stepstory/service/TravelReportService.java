package com.kcs.stepstory.service;

import com.kcs.stepstory.domain.*;
import com.kcs.stepstory.dto.request.*;
import com.kcs.stepstory.dto.response.*;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.exception.ErrorCode;
import com.kcs.stepstory.repository.*;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelReportService {
    private final TravelBodyRepository travelBodyRepository;
    private final StepRepository stepRepository;
    private final TravelImageRepository travelImageRepository;
    private final DetailCourseRepository detailCourseRepository;
    private final TravelReportRepository travelReportRepository;
    private final WantToGoRepository wantToGoRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public TravelReportListDto getTravelReportList(String province, String city, String district) {
        List<Long> travelReportIds = stepRepository.findByProvinceAndCityAndDistrict(province, city, district)
                .stream()
                .map(Step::getTravelReport)
                .map(TravelReport::getTravelReportId)
                .distinct()
                .collect(Collectors.toList());

        List<TravelBody> filteredBodies = travelBodyRepository.findByTravelReportIdInAndReadPermissionEquals(travelReportIds, 1);

        List<TravelReportDto> reportDtos = filteredBodies.stream()
                .map(TravelBody::getTravelReport)
                .map(TravelReportDto::fromEntity)
                .collect(Collectors.toList());

        return TravelReportListDto.fromEntity(reportDtos);
    }

    /*
     * 게시글 작성
     * 게시글 사진 & 코스 확인 페이지(Get)
     * TravelImageList & DetailCourseList 가져오기
     * Controller에 보낼 Service
     * */
    public CheckTravelImageListDto getCheckTravelImageList(Long travelReportId){
        List<TravelImage> travelImages = travelImageRepository.getTravelImagesByTravelReport(travelReportId);

        List<CheckTravelImageDto> checkTravelImageListDtos = travelImages
                .stream()
                .map(CheckTravelImageDto::fromEntity)
                .collect(Collectors.toList());
        return CheckTravelImageListDto.fromEntity(checkTravelImageListDtos);
    }

    /*
     * 게시글 작성
     * 게시글 사진 & 코스 확인 페이지(Patch)
     * Update TravelImage List
     * Controller에 보낼 Service
     * */
    @Transactional
    public PostTravelImageListDto updateImages(PostTravelImageListDto postTravelImageListDto){
        List<PostTravelImageDto> postTravelImageDtos = postTravelImageListDto.postTravelImageDtoList();

        for(PostTravelImageDto postTravelImageDto : postTravelImageDtos){
            TravelImage travelImage = travelImageRepository.findById(postTravelImageDto.travelImageId());
            DetailCourse detailCourse = detailCourseRepository.findById(postTravelImageDto.detailCourse().getDetailCourseId());
            detailCourse.updateDetailCourse(
                    postTravelImageDto.detailCourse().getTravelDate(),
                    postTravelImageDto.detailCourse().getGps(),
                    postTravelImageDto.detailCourse().getSequence(),
                    postTravelImageDto.detailCourse().getLocationName());
            travelImage.updateTravelImage(detailCourse, postTravelImageDto.imageUrl());
        }

        return postTravelImageListDto;
    }

    /*
     * DetailCouse 추가(Post)
     * 게시글 사진 & 코스 확인 페이지
     * Insert DetailCourse(수정 필요)
     * */
    @Transactional
    public void addDetailCourse(AddDetailCourseDto addDetailCourseDto){
        TravelReport travelReport = travelReportRepository.getReferenceById(addDetailCourseDto.travelReportId());
        DetailCourse newDetailCourse = DetailCourse.builder()
                .travelReport(travelReport)
                .travelDate(addDetailCourseDto.travelDate())
                .gps(addDetailCourseDto.gps())
                .sequence(addDetailCourseDto.sequence())
                .locationName(addDetailCourseDto.locationName())
                .build();
        detailCourseRepository.saveAndFlush(newDetailCourse);
    }

    /*
     * 게시글 최종 작성 페이지(Get)
     * TravelImage 리스트 & User(닉네임, profileImageUrl)
     * 처음 게시글 최종 작성 페이지에 들어왔을 때 service
     *  */
    public WriteReportTravelImageListDto getWriteTravelImageList(Long travelReportId){
        List<TravelImage> travelImages = travelImageRepository.getTravelImagesByTravelReport(travelReportId);

        List<WriteReportTravelImageDto> writeReportTravelImageDtos = travelImages
                .stream()
                .map(WriteReportTravelImageDto::fromEntity)
                .collect(Collectors.toList());
        return WriteReportTravelImageListDto.fromEntity(writeReportTravelImageDtos);
    }

    /*
     * 게시글 최종 작성 페이지(Patch)
     * Travelreport의 title, TravelreportBody의 body, readPermission update
     *  */
    @Transactional
    public PostWriteTravelReportDto updateFinalTravelReport(PostWriteTravelReportDto postWriteTravelReportDto){
        TravelReport travelReport = travelReportRepository.getReferenceById(postWriteTravelReportDto.travelReportId());
        TravelBody travelBody = travelBodyRepository.getReferenceById(postWriteTravelReportDto.travelReportId());

        travelReport.updateTravelReportTitle(postWriteTravelReportDto.title());
        travelBody.updateTravelBody(postWriteTravelReportDto.body(), postWriteTravelReportDto.readPermission());

        return postWriteTravelReportDto;
    }

    /*
     * 게시글 상세보기(Get)
     * Get ViewTravelReport
     * */
    public ViewTravelReportDto getTravelReport(Long travelReportId){
        List<TravelImage> travelImages = travelImageRepository.getTravelImagesByTravelReport(travelReportId);
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        TravelBody travelBody = travelBodyRepository.getReferenceById(travelReportId);

        List<ViewTravelImageDto> viewTravelImageDtos = travelImages
                .stream()
                .map(ViewTravelImageDto::fromEntity)
                .collect(Collectors.toList());

        ViewTravelImageListDto viewTravelImageListDto = ViewTravelImageListDto.fromEntity(viewTravelImageDtos);

        CommonUserSourseDto commonUserSourseDto = CommonUserSourseDto.fromEntity(travelReport.getUser());

        return ViewTravelReportDto.builder()
                .viewTravelImageListDto(viewTravelImageListDto)
                .travelReportId(travelReport.getTravelReportId())
                .commonUserSourseDto(commonUserSourseDto)
                .title(travelReport.getTitle())
                .travelPeriod(travelReport.getTravelPeriod())
                .travelLocation(travelReport.getTravelLocation())
                .thumbnailUrl(travelReport.getThumbnailUrl())
                .createdAt(travelReport.getCreatedAt())
                .updatedAt(travelReport.getUpdatedAt())
                .wantToGoCount(travelReport.getWantToGoCount())
                .body(travelBody.getBody())
                .build();
    }

    /*
     * 게시물-가고싶어요 버튼 누름(Post)
     * 가고싶어요 눌러져 있지 않으면 증가 -> WantToGo 테이블에 없으면
     * 가고싶어요 눌러져 있으면 감소 -> WantTOGo 테이블에서 삭제 -> TravelReport.wantToGo(감소)
     * Transactional 확인 필요 deleteById와 update가 동시에 진행
     * */
    @Transactional
    public Long pushWantToGo(Long userId, Long travelReportId){
        User user = userRepository.getReferenceById(userId);
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        WantToGoId wantToGoId = new WantToGoId(user, travelReport);
        Boolean wantToGo = wantToGoRepository.existsById(wantToGoId);

        if(wantToGo){
            wantToGoRepository.deleteById(wantToGoId);
            travelReport.updateTravelReportWantToGoCount(travelReport.getWantToGoCount()-1);
            return travelReport.getWantToGoCount();
        }else{
            wantToGoRepository.saveAndFlush(new WantToGo(user, travelReport));
            travelReport.updateTravelReportWantToGoCount(travelReport.getWantToGoCount()+1);
            return travelReport.getWantToGoCount();
        }
    }

    /*
     * 게시글 삭제(Delete)
     * 권한이 있는지 확인 -> 본인이 쓴 글이 맞는지 확인하고 삭제 진행
     *  */
    public Long deleteTravelReport(Long userId, Long travelReportId){
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);

        if(!userId.equals(travelReport.getUser().getUserId())){
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }

        travelReportRepository.deleteById(travelReportId);

        return travelReportId;
    }

    /*
     * 게시글 수정(Get)
     * 수정 첫번째 페이지
     * 이미지 업로드, 업로드 된 이미지 삭제, 여행 기간 변경
     *  */
    public ModifyTravelReportFirstPageDto modifyTravelReportFirst(Long travelReportId, Long userId){
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        List<TravelImage> travelImages = travelImageRepository.getTravelImagesByTravelReport(travelReportId);

        if(!userId.equals(travelReport.getUser().getUserId())){
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        List<ModifyTravelImageDto> modifyTravelImageDtos = travelImages
                .stream()
                .map(ModifyTravelImageDto::fromEntity)
                .collect(Collectors.toList());

        ModifyTravelImageListDto modifyTravelImageListDto = ModifyTravelImageListDto.builder()
                .modifyTravelImageDtos(modifyTravelImageDtos)
                .build();

        return ModifyTravelReportFirstPageDto.fromEntity(modifyTravelImageListDto, travelReport);
    }

    /*
     * 댓글 조회(Get)
     * travelId를 받아서 해당 게시글의 댓글을 조회하는 기능
     * */
    public ViewCommentListDto viewCommentList(Long travelReportId){
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        List<Comment> comments = commentRepository.findCommentsByTravelReportOrderByCreatedAtAsc(travelReport);

        List<ViewCommentDto> viewCommentDtos = comments
                .stream()
                .map(ViewCommentDto::fromEntity)
                .collect(Collectors.toList());

        return ViewCommentListDto.fromEntity(viewCommentDtos);
    }

    /*
     * 댓글 작성(Post)
     * WriteCommentDto를 받아 데이터 처리
     *  */
    public void writeComment(WriteCommentDto writeCommentDto, Long userId){
        Comment comment = Comment.builder()
                .travelReport(writeCommentDto.travelReport())
                .user(userRepository.getReferenceById(userId))
                .content(writeCommentDto.content())
                .parentCommentId(writeCommentDto.parentCommentId())
                .build();
        commentRepository.saveAndFlush(comment);
    }

    /*
     * 댓글 수정(Patch)
     * UpdateCommentDto를 받아 댓글 수정
     *  */
    @Transactional
    public void updateComment(UpdateCommentDto updateCommentDto, Long userId){
        Comment comment = commentRepository.findCommentByCommentId(updateCommentDto.commentId());
        if(!userId.equals(comment.getUser().getUserId())){
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }
        comment.updateComment(updateCommentDto.content());
    }
}
