package com.kcs.stepstory.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import com.kcs.stepstory.domain.*;
import com.kcs.stepstory.dto.request.*;
import com.kcs.stepstory.dto.response.*;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.exception.ErrorCode;
import com.kcs.stepstory.repository.*;
import com.kcs.stepstory.utility.ImageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
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
    private final ImageUtil imageUtil;
    private final FriendRepository friendRepository;


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

    public TravelReportListDto getMyTravelReportList(String province, String city, String district, String nickname, Long userId) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }

        List<Long> travelReportIds = stepRepository.findStepsByProvinceAndCityAndDistrictAndUser(province, city, district, user)
                .stream()
                .map(Step::getTravelReport)
                .map(TravelReport::getTravelReportId)
                .distinct()
                .collect(Collectors.toList());

        List<TravelBody> filteredBodies;

        // 본인, 친구 관계, 친구가 아닌 관계
        if(user.getUserId().equals(userId)){
            filteredBodies = travelBodyRepository.findTravelBodiesByTravelReportIdIn(travelReportIds);
        }else if(
                friendRepository.existsByUser1UserIdAndUser2UserIdAndStatus(user.getUserId(), userId, 1) || friendRepository.existsByUser1UserIdAndUser2UserIdAndStatus(userId, user.getUserId(), 1)
        ){
            int[] onlyFriend = {1, 2};
            filteredBodies = travelBodyRepository.findTravelBodiesByTravelReportIdInAndReadPermissionIsIn(travelReportIds, onlyFriend);
        }else{
            filteredBodies = travelBodyRepository.findTravelBodiesByTravelReportIdInAndReadPermissionEquals(travelReportIds, 1);
        }

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
    public PostTravelImageListDto updateImagesAndDetailCourse(List<PostTravelImageDto> postTravelImageDtos) {

        for (PostTravelImageDto postTravelImageDto : postTravelImageDtos) {
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
    public AddDetailCourseDto addDetailCourse(AddDetailCourseDto addDetailCourseDto) {
        TravelReport travelReport = travelReportRepository.getReferenceById(addDetailCourseDto.travelReportId());
        DetailCourse newDetailCourse = DetailCourse.builder()
                .travelReport(travelReport)
                .travelDate(addDetailCourseDto.travelDate())
                .latitude(addDetailCourseDto.latitude())
                .longitude(addDetailCourseDto.longitude())
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
    public WriteTravelReportDto getTravelImagesAndDetailCourses(Long travelReportId) {
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
    public PostWriteTravelReportDto updateFinalTravelReport(PostWriteTravelReportDto postWriteTravelReportDto) {
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
    public ViewTravelReportDto getTravelReport(Long travelReportId) {
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
    public Long pushWantToGo(Long userId, Long travelReportId) {
        User user = userRepository.getReferenceById(userId);
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        WantToGo wantToGo = wantToGoRepository.getByUserAndTravelReport(user, travelReport);

        if (wantToGo != null) {
            wantToGoRepository.delete(wantToGo);
            travelReport.updateTravelReportWantToGoCount(travelReport.getWantToGoCount() - 1);
            return travelReport.getWantToGoCount();
        } else {
            wantToGoRepository.save(new WantToGo(user, travelReport));
            travelReport.updateTravelReportWantToGoCount(travelReport.getWantToGoCount() + 1);
            return travelReport.getWantToGoCount();
        }
    }

    /*
     * 게시글 삭제(Delete)
     * 권한이 있는지 확인 -> 본인이 쓴 글이 맞는지 확인하고 삭제 진행
     *  */
    public void deleteTravelReport(Long userId, Long travelReportId) {
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);

        if (!userId.equals(travelReport.getUser().getUserId())) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }

        travelReportRepository.deleteById(travelReportId);
    }

    /*
     * 게시글 수정(Get)
     * 수정 첫번째 페이지
     * 이미지 업로드, 업로드 된 이미지 삭제, 여행 기간 변경
     *  */
    public ModifyTravelReportFirstPageDto modifyTravelReportFirst(Long travelReportId, Long userId) {
        TravelReport travelReport = travelReportRepository.getReferenceById(travelReportId);
        if (!userId.equals(travelReport.getUser().getUserId())) {
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
    public ViewCommentListDto viewCommentList(Long travelReportId) {
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
    public Long writeComment(WriteCommentDto writeCommentDto, Long userId) {
        Comment comment;
        if (writeCommentDto.parentCommentId() == null) {
            comment = Comment.builder()
                    .travelReport(travelReportRepository.getReferenceById(writeCommentDto.travelReportId()))
                    .user(userRepository.getReferenceById(userId))
                    .content(writeCommentDto.content())
                    .build();
        } else {
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
    public void updateComment(UpdateCommentDto updateCommentDto, Long userId) {
        Comment comment = commentRepository.findCommentByCommentId(updateCommentDto.commentId());
        if (!userId.equals(comment.getUser().getUserId())) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }
        comment.updateComment(updateCommentDto.content());
    }

    /*
     * 댓글 삭제(Delete)
     * commentId를 받아 댓글 삭제
     *  */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findCommentByCommentId(commentId);

        if (!userId.equals(comment.getUser().getUserId())) {
            throw new CommonException(ErrorCode.NOT_MATCH_USER);
        }
        commentRepository.deleteByCommentId(commentId);
    }

    @Transactional
    public ImageMetaDataListDto getUploadTravelImageMeta(List<MultipartFile> multipartFiles) throws ImageProcessingException, IOException{
        List<ImageMetaDataDto> uploadImageMetaDataDtos = getMetaData(multipartFiles);
        return ImageMetaDataListDto.builder().imageMetaDataDtos(uploadImageMetaDataDtos).build();
    }

    /*
     * 사진 업로드 후 게시글 작성(완료버튼 누름)
     * */
    @Transactional
    public TravelReportDto writeFirstReportStep(
            Long userId,
            UploadTravelImageDto requestDto,
            List<MultipartFile> multipartFiles
    ) throws ImageProcessingException, IOException {

        User user = userRepository.getReferenceById(userId);

        List<String> imageUrls = multipartFiles.stream()
                .map(multipartFile -> {
                    return imageUtil.uploadTravelImageFile(multipartFile, userId);
                })
                .toList();
        String thumbnailUrl = imageUrls.get(requestDto.thumbnailIndex());

        List<ImageUrlAndMetaDataDto> imageUrlAndMetaDataDtos = getUploadTravelImageMeta(imageUrls, multipartFiles);
        imageUrlAndMetaDataDtos = sortImageUrlAndMetaDataDto(imageUrlAndMetaDataDtos);
        List<DetailCourseDto> detailCourseDtos = new ArrayList<>();
        List<StepDto> stepDtos = new ArrayList<>();
        for (int i = 0; i < imageUrlAndMetaDataDtos.size(); i++) {

            String locationName = coordToAddr(imageUrlAndMetaDataDtos.get(i).longitude(), imageUrlAndMetaDataDtos.get(i).latitude());
            detailCourseDtos.add(
                    DetailCourseDto.builder()
                            .travelDate(imageUrlAndMetaDataDtos.get(i).travelDate())
                            .latitude(imageUrlAndMetaDataDtos.get(i).latitude())
                            .longitude(imageUrlAndMetaDataDtos.get(i).longitude())
                            .locationName(locationName)
                            .sequence(i + 1)
                            .build()
            );

            String province = locationName.split(" ")[0];
            String city = locationName.split(" ")[1];
            String district = locationName.split(" ")[2];

            stepDtos.add(
                    StepDto.builder()
                            .province(province)
                            .city(city)
                            .district(district)
                            .build()
            );
        }

        TravelReport travelReport= travelReportRepository.save(
                TravelReport.builder()
                        .user(user)
                        .thumbnailUrl(thumbnailUrl)
                        .title("")
                        .build()
        );


        for (DetailCourseDto detailCourcedto : detailCourseDtos) {
            DetailCourse detailCourse = createDetailCourse(detailCourcedto, travelReport);
            createTravelImage(imageUrlAndMetaDataDtos.get(detailCourcedto.sequence() - 1), detailCourse, travelReport);
        }
        createStep(stepDtos, travelReport, userId);
        createBody(travelReport);
        return TravelReportDto.fromEntity(travelReport);
    }
    public List<ImageMetaDataDto> getMetaData(List<MultipartFile> multipartFiles) throws IOException, ImageProcessingException {
        List<ImageMetaDataDto> uploadImageMetaDataDtos = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());

            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            ExifIFD0Directory dateDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            Double latitude = null;
            Double longitude = null;
            Date date = null;

            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null) {
                    latitude = geoLocation.getLatitude();
                    longitude = geoLocation.getLongitude();
                }
            }

            if (dateDirectory != null) {
                date = dateDirectory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault());
            }

            uploadImageMetaDataDtos.add(
                    ImageMetaDataDto.builder()
                            .travelDate((Timestamp) date)
                            .latitude(latitude)
                            .longitude(longitude)
                            .build());
        }
        uploadImageMetaDataDtos.sort((o1, o2) -> o1.travelDate().compareTo(o2.travelDate()));

        return uploadImageMetaDataDtos;
    }

    /*
     * Url + Multipart -> Dto(url,date,latitue,longitude) 변환
     *
     * */
    public List<ImageUrlAndMetaDataDto> getUploadTravelImageMeta(List<String> imageUrls ,List<MultipartFile> multipartFiles) throws IOException, ImageProcessingException {
        List<ImageUrlAndMetaDataDto> imageUrlAndMetaDataDtos = new ArrayList<>();

        for (int i=0; i<imageUrls.size(); i++) {
            MultipartFile multipartFile = multipartFiles.get(i);
            String imageUrl = imageUrls.get(i);
            Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());

            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            ExifIFD0Directory dateDirectory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            Double latitude = null;
            Double longitude = null;
            Date date = null;

            if (gpsDirectory != null) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null) {
                    latitude = geoLocation.getLatitude();
                    longitude = geoLocation.getLongitude();
                }
            }

            if (dateDirectory != null) {
                date = dateDirectory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL, TimeZone.getDefault());
                if(date == null)
                    date = Date.from(Instant.now());
                log.info("date : " + date);
            }

            Timestamp timestamp = date != null ? new Timestamp(date.getTime()) : null;

            imageUrlAndMetaDataDtos.add(
                    ImageUrlAndMetaDataDto.builder()
                            .imageUrl(imageUrl)
                            .travelDate(timestamp) // 수정된 부분
                            .latitude(latitude)
                            .longitude(longitude)
                            .build());
        }

        return imageUrlAndMetaDataDtos;
    }

    public List<ImageUrlAndMetaDataDto> sortImageUrlAndMetaDataDto(List<ImageUrlAndMetaDataDto> imageUrlAndMetaDataDtos) {
        imageUrlAndMetaDataDtos.sort((o1, o2) -> o1.travelDate().compareTo(o2.travelDate()));
        return imageUrlAndMetaDataDtos;
    }

    public String coordToAddr(Double longitude, Double latitude){
        log.info("coordToAddr : " + longitude + " " + latitude);
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?input_coord=WGS84&x="+longitude.toString()+"&y="+latitude.toString();
        String addr = "";
        try{
            log.info("getJsonData : " + getJSONData(url));
            addr = getRegionAddress(getJSONData(url));
        }catch(Exception e){
            e.printStackTrace();
        }
        return addr;
    }

    /**
     * REST API로 통신하여 받은 JSON형태의 데이터를 String으로 받아오는 메소드
     */
    private static String getJSONData(String apiUrl) throws Exception {
        HttpURLConnection conn = null;
        StringBuffer response = new StringBuffer();

        // 인증키
        String auth = "KakaoAK " + "c989969c7ae63def6870b9b88e926b51";

        //URL 설정
        URL url = new URL(apiUrl);

        conn = (HttpURLConnection) url.openConnection();

        //Request 형식 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", auth);

        //request에 JSON data 준비
        conn.setDoOutput(true);

        //보내고 결과값 받기
        int responseCode = conn.getResponseCode();
        if (responseCode == 400) {
            System.out.println("400:: 해당 명령을 실행할 수 없음");
        } else if (responseCode == 401) {
            System.out.println("401:: Authorization가 잘못됨");
        } else if (responseCode == 500) {
            System.out.println("500:: 서버 에러, 문의 필요");
        } else { // 성공 후 응답 JSON 데이터받기
            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
        }
        log.info("response.toString() : " + response.toString());
        return response.toString();
    }

    /**
     * JSON형태의 String 데이터에서 주소값(address_name)만 받아오기
     */
    private static String getRegionAddress(String jsonString) {
        log.info("jsonString : " + jsonString);
        log.info("JSONValue.parse(jsonString) : " + JSONValue.parse(jsonString));
        JSONObject jObj = (JSONObject) JSONValue.parse(jsonString);
        JSONArray documents = (JSONArray) jObj.get("documents");

        if (documents.size() > 0) {
            JSONObject firstDocument = (JSONObject) documents.get(0);
            JSONObject address;

            // road_address가 있는지 확인하고, 없으면 address를 사용
            if (firstDocument.get("road_address") != null) {
                address = (JSONObject) firstDocument.get("road_address");
            } else {
                address = (JSONObject) firstDocument.get("address");
            }

            // "address_name"을 반환
            return (String) address.get("address_name");
        }

        return ""; // 문서가 없을 경우 빈 문자열 반환
    }

    public DetailCourse createDetailCourse(DetailCourseDto detailCourseDto, TravelReport travelReport) {
        return detailCourseRepository.save(
                DetailCourse.builder()
                        .travelReport(travelReport)
                        .travelDate(detailCourseDto.travelDate())
                        .latitude(detailCourseDto.latitude())
                        .longitude(detailCourseDto.longitude())
                        .locationName(detailCourseDto.locationName())
                        .sequence(detailCourseDto.sequence())
                        .build()
        );
    }
    public void createTravelImage(ImageUrlAndMetaDataDto imageUrlAndMetaDataDto, DetailCourse detailCourse, TravelReport travelReport) {
        TravelImage travelImage = travelImageRepository.save(
                TravelImage.builder()
                        .travelReport(travelReport)
                        .imageUrl(imageUrlAndMetaDataDto.imageUrl())
                        .detailCourse(detailCourse)
                        .build()
        );
    }
    public void createStep(List<StepDto> stepDtos, TravelReport travelReport, Long userId) {
        Set<String> provinces = new HashSet<>();
        Set<String> cities = new HashSet<>();
        Set<String> districts = new HashSet<>();
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        for(StepDto stepDto : stepDtos) {
            log.info("stepDto : " + stepDto.province() + " " + stepDto.city() + " " + stepDto.district());
            if(stepDto.province().equals("서울") || stepDto.province().equals("서울특별시")) {
                provinces.add("seoul");
                cities.add("seoul");
                districts.add(SeoulconvertDistrict(stepDto));
            }
            else {
                provinces.add(stepDto.province());
                cities.add(stepDto.city());
                if(stepDto.district() != null) {
                    districts.add(stepDto.district());
                }
            }
        }
        for(String province : provinces) {
            for(String city : cities) {
                for(String district : districts) {
                    stepRepository.save(
                            Step.builder()
                                    .user(user)
                                    .travelReport(travelReport)
                                    .province(province)
                                    .city(city)
                                    .district(district)
                                    .build()
                    );
                }
            }
        }
    }
    public void createBody(TravelReport travelReport) {
        travelBodyRepository.save(
                TravelBody.builder()
                        .travelReport(travelReport)
                        .body("")
                        .readPermission(1)
                        .build()
        );
    }

    public String SeoulconvertDistrict(StepDto stepDto){
        switch (stepDto.city()){
            case "종로구":
                return "jongro";
            case "중구":
                return "jung";
            case "용산구":
                return "yongsan";
            case "성동구":
                return "seongdong";
            case "광진구":
                return "gwangjin";
            case "동대문구":
                return "dongdaemun";
            case "중랑구":
                return "jungrang";
            case "성북구":
                return "seongbuk";
            case "강북구":
                return "gangbuk";
            case "도봉구":
                return "dobong";
            case "노원구":
                return "nowon";
            case "은평구":
                return "eunpyeong";
            case "서대문구":
                return "seodaemun";
            case "마포구":
                return "mapo";
            case "양천구":
                return "yangcheon";
            case "강서구":
                return "gangseo";
            case "구로구":
                return "guro";
            case "금천구":
                return "geumcheon";
            case "영등포구":
                return "yeongdeungpo";
            case "동작구":
                return "dongjak";
            case "관악구":
                return "gwanak";
            case "서초구":
                return "seocho";
            case "강남구":
                return "gangnam";
            case "송파구":
                return "songpa";
            case "강동구":
                return "gangdong";
            default:
                return stepDto.district();
        }
    }
}
