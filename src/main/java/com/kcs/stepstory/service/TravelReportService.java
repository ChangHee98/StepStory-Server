package com.kcs.stepstory.service;

import com.kcs.stepstory.domain.*;
import com.kcs.stepstory.dto.request.*;
import com.kcs.stepstory.dto.response.*;
import com.kcs.stepstory.repository.*;
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
}
