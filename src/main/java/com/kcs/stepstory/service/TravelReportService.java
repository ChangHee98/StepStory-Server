package com.kcs.stepstory.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import com.kcs.stepstory.domain.*;
import com.kcs.stepstory.dto.request.*;
import com.kcs.stepstory.dto.response.*;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.exception.ErrorCode;
import com.kcs.stepstory.repository.*;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
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
        List<Long> travelReportIds = stepRepository.findStepsByProvinceAndCityAndDistrict(province, city, district)
                .stream()
                .map(Step::getTravelReport)
                .map(TravelReport::getTravelReportId)
                .distinct()
                .collect(Collectors.toList());

        List<TravelBody> filteredBodies = travelBodyRepository.findTravelBodiesByTravelReportIdInAndReadPermissionEquals(travelReportIds, 1);

        List<TravelReportDto> reportDtos = filteredBodies.stream()
                .map(TravelBody::getTravelReport)
                .map(TravelReportDto::fromEntity)
                .collect(Collectors.toList());
                
        return TravelReportListDto.fromEntity(reportDtos);
    }

    public TravelReportListDto getMyTravelReportList(String province, String city, String district, String nickname) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        List<Long> travelReportIds = stepRepository.findStepsByProvinceAndCityAndDistrictAndUser(province, city, district,user)
                .stream()
                .map(Step::getTravelReport)
                .map(TravelReport::getTravelReportId)
                .distinct()
                .collect(Collectors.toList());

        List<TravelBody> filteredBodies = travelBodyRepository.findTravelBodiesByTravelReportIdInAndReadPermissionEquals(travelReportIds, 1);

        List<TravelReportDto> reportDtos = filteredBodies.stream()
                .map(TravelBody::getTravelReport)
                .map(TravelReportDto::fromEntity)
                .collect(Collectors.toList());

        return TravelReportListDto.fromEntity(reportDtos);
    }

    /*
     * 게시글 작성
     * 게시글 사진 & 코스 확인 페이지(Patch)
     * Update TravelImage List
     * Controller에 보낼 Service
     * */
    @Transactional
    public PostTravelImageListDto updateImagesAndDetailCourse(List<PostTravelImageDto> postTravelImageDtos){

        for(PostTravelImageDto postTravelImageDto : postTravelImageDtos){
            TravelImage travelImage = travelImageRepository.findByTravelImageId(postTravelImageDto.travelImageId());
            DetailCourse detailCourse = detailCourseRepository.findByDetailCourseId(postTravelImageDto.detailCourseId());
            detailCourse.updateDetailCourse(
                    postTravelImageDto.sequence(),
                    postTravelImageDto.locationName()
            );
            travelImage.updateTravelImage(detailCourse);
        }

        return PostTravelImageListDto.fromEntity(postTravelImageDtos);
    }

    /*
     * DetailCouse 추가(Post)
     * 게시글 사진 & 코스 확인 페이지
     * Insert DetailCourse(수정 필요)
     * */
    @Transactional
    public AddDetailCourseDto addDetailCourse(AddDetailCourseDto addDetailCourseDto){
        TravelReport travelReport = travelReportRepository.getReferenceById(addDetailCourseDto.travelReportId());
        DetailCourse newDetailCourse = DetailCourse.builder()
                .travelReport(travelReport)
                .travelDate(addDetailCourseDto.travelDate())
                .gps(addDetailCourseDto.gps())
                .sequence(addDetailCourseDto.sequence())
                .locationName(addDetailCourseDto.locationName())
                .build();
        detailCourseRepository.saveAndFlush(newDetailCourse);

        return addDetailCourseDto;
    }

    /*
     * 게시글 최종 작성 페이지(Get)
     * TravelImage 리스트 & User(닉네임, profileImageUrl)
     * 처음 게시글 최종 작성 페이지에 들어왔을 때 service
     *  */
    public WriteTravelReportDto getTravelImagesAndDetailCourses(Long travelReportId){
        List<TravelImage> travelImages = travelImageRepository.findTravelImagesByTravelReport(travelReportRepository.getReferenceById(travelReportId));

        List<WriteReportTravelImageDto> writeReportTravelImageDtos = travelImages
                .stream()
                .map(WriteReportTravelImageDto::fromEntity)
                .collect(Collectors.toList());
        WriteReportTravelImageListDto writeReportTravelImageListDto = WriteReportTravelImageListDto.builder().writeReportTravelImageDtos(writeReportTravelImageDtos).build();

        return WriteTravelReportDto.builder()
                .writeReportTravelImageListDto(writeReportTravelImageListDto)
                .thumbnailUrl(travelReportRepository.getReferenceById(travelReportId).getThumbnailUrl())
                .build();
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
        List<TravelImage> travelImages = travelImageRepository.getTravelImagesByTravelReport(travelReportRepository.getReferenceById(travelReportId));
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        TravelBody travelBody = travelBodyRepository.getReferenceById(travelReportId);

        List<ViewTravelImageDto> viewTravelImageDtos = travelImages
                .stream()
                .map(ViewTravelImageDto::fromEntity)
                .collect(Collectors.toList());

        ViewTravelImageListDto viewTravelImageListDto = ViewTravelImageListDto.builder()
                .viewTravelImageDtos(viewTravelImageDtos)
                .build();

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
        WantToGo wantToGo = wantToGoRepository.getByUserAndTravelReport(user, travelReport);

        if(wantToGo != null){
            wantToGoRepository.delete(wantToGo);
            travelReport.updateTravelReportWantToGoCount(travelReport.getWantToGoCount()-1);
            return travelReport.getWantToGoCount();
        }else{
            wantToGoRepository.save(new WantToGo(user, travelReport));
            travelReport.updateTravelReportWantToGoCount(travelReport.getWantToGoCount()+1);
            return travelReport.getWantToGoCount();
        }
    }

    /*
     * 게시글 삭제(Delete)
     * 권한이 있는지 확인 -> 본인이 쓴 글이 맞는지 확인하고 삭제 진행
     *  */
    public void deleteTravelReport(Long userId, Long travelReportId){
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);

        if(!userId.equals(travelReport.getUser().getUserId())){
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }

        travelReportRepository.deleteById(travelReportId);
    }

    /*
     * 게시글 수정(Get)
     * 수정 첫번째 페이지
     * 이미지 업로드, 업로드 된 이미지 삭제, 여행 기간 변경
     *  */
    public ModifyTravelReportFirstPageDto modifyTravelReportFirst(Long travelReportId, Long userId){
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        if(!userId.equals(travelReport.getUser().getUserId())){
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        List<TravelImage> travelImages = travelImageRepository.getTravelImagesByTravelReport(travelReportRepository.getReferenceById(travelReportId));

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

        return ViewCommentListDto.builder()
                .viewCommentDtos(viewCommentDtos)
                .build();
    }

    /*
     * 댓글 작성(Post)
     * WriteCommentDto를 받아 데이터 처리
     *  */
    public Long writeComment(WriteCommentDto writeCommentDto, Long userId){
        Comment comment;
        if(writeCommentDto.parentCommentId()==null){
            comment = Comment.builder()
                    .travelReport(travelReportRepository.getReferenceById(writeCommentDto.travelReportId()))
                    .user(userRepository.getReferenceById(userId))
                    .content(writeCommentDto.content())
                    .build();
        }else{
            comment = Comment.builder()
                    .travelReport(travelReportRepository.getReferenceById(writeCommentDto.travelReportId()))
                    .user(userRepository.getReferenceById(userId))
                    .content(writeCommentDto.content())
                    .parentCommentId(writeCommentDto.parentCommentId())
                    .build();
        }
        comment = commentRepository.saveAndFlush(comment);

        return comment.getCommentId();
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

    /*
     * 댓글 삭제(Delete)
     * commentId를 받아 댓글 삭제
     *  */
    @Transactional
    public void deleteComment(Long commentId, Long userId){
        Comment comment = commentRepository.findCommentByCommentId(commentId);

        if(!userId.equals(comment.getUser().getUserId())){
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }
        commentRepository.deleteByCommentId(commentId);
    }

    /*
     * 업로드 된 메타데이터를 확인하여 gps랑 시작, 마지막 날짜 반환
     * 메타데이터 확인
     * */
    public ImageMetaDataListDto getUploadTravelImageMeta(List<MultipartFile> multipartFiles) throws IOException, ImageProcessingException {
        Timestamp startDay = new Timestamp(System.currentTimeMillis());
        Timestamp endDay = Timestamp.valueOf("1970-01-01 00:00:00");

        List<ImageMetaDataDto> imageMetaDataDtos = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            String pdsLat = "";
            String pdsLon = "";
            String latAndLon = "";
            Timestamp imageDate = null;

            if(gpsDirectory == null && directory == null){
                log.info("gps 및 date의 정보 없음");
                continue;
            }

            if(directory != null){
                Date date = directory.getDate(ExifIFD0Directory.TAG_DATETIME, TimeZone.getTimeZone("Asia/Seoul"));
                if(date == null){
                    continue;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = dateFormat.format(date);
                imageDate = Timestamp.valueOf(dateString);

                if(imageDate.before(startDay)){
                    startDay = imageDate;
                }
                if(imageDate.after(endDay)){
                    endDay = imageDate;
                }

            }else{
                log.info("travelDate 메타 데이터 없음");
            }

            if(gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE) && gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE)){
                pdsLat = String.valueOf(gpsDirectory.getGeoLocation().getLatitude());   // 위도
                pdsLon = String.valueOf(gpsDirectory.getGeoLocation().getLongitude());  // 경도

                latAndLon = pdsLat + ", " + pdsLon;
            }else{
                log.info("위치 메타데이터 없음");
            }

            ImageMetaDataDto imageMetaDataDto = ImageMetaDataDto.builder()
                    .gps(latAndLon)
                    .travelDate(imageDate)
                    .build();
            imageMetaDataDtos.add(imageMetaDataDto);
        }// for

        return ImageMetaDataListDto.builder()
                .imageMetaDataDtos(imageMetaDataDtos)
                .startDay(startDay.toString())
                .endDay(endDay.toString())
                .build();
    }

    /*
     * 메타데이터를 반환해주는 메소드
     * gps와 startDay, endDay 반환
     * */
    public UploadImageMetaDataDto getUploadImageMetaData(List<MultipartFile> multipartFiles) throws IOException, ImageProcessingException {
        ImageMetaDataListDto imageMetaDataListDto = getUploadTravelImageMeta(multipartFiles);
        List<String> gpsList = new ArrayList<>();
        for(ImageMetaDataDto imageMetaDataDto : imageMetaDataListDto.imageMetaDataDtos()){
            gpsList.add(imageMetaDataDto.gps());
        }
        return UploadImageMetaDataDto.builder()
                .gps(gpsList)
                .startDay(imageMetaDataListDto.startDay())
                .endDay(imageMetaDataListDto.endDay())
                .build();
    }

    /*
     * 사진 업로드 후 게시글 작성(완료버튼 누름)
     * */
    @Transactional
    public void postTravelReportFirst(
            Long userId,
            List<MultipartFile> multipartFiles
    ) throws ImageProcessingException, IOException {

//        ImageMetaDataListDto imageMetaDataListDto = getUploadTravelImageMeta(multipartFiles);
//
//        User user = userRepository.getReferenceById(userId);
//        String[] location = travelLocation.split(" ");
//
//        TravelReport travelReport = travelReportRepository.saveAndFlush(TravelReport.builder()
//                .user(user)
//                .title("")
//                .travelPeriod(travelPeriod)
//                .travelLocation(travelLocation)
//                .thumbnailUrl()
//                .build());
//
//        travelBodyRepository.saveAndFlush(TravelBody.builder()
//                .travelReport(travelReport)
//                .body("")
//                .readPermission(1)
//                .build());
//
//        stepRepository.saveAndFlush(Step.builder()
//                .travelReport(travelReport)
//                .user(user)
//                .province(location[0])
//                .city(location[1])
//                .district(location[2])
//                .build());
//
////      S3에 MultipartFile을 저장한 후 업로드 된 데이터를 기반하여 DetailCourse를 뽑아낸다.
//        detailCourseRepository.saveAndFlush(DetailCourse.builder()
//                        .travelReport(travelReport)
//                        .travelDate()
//                        .gps()
//                        .sequence()
//                        .locationName()
//                .build());
//
////      S3에 MultipartFile을 저장한 후 S3에 주소를 가져와 imageUrl에 넣어준다.
//        travelImageRepository.saveAndFlush(TravelImage.builder()
//                .travelReport(travelReport)
//                .imageUrl()
//                .detailCourse()
//                .build());



    }
}
