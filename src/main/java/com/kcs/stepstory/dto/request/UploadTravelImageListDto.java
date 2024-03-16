package com.kcs.stepstory.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadTravelImageListDto(
        List<MultipartFile> multipartFiles
) {

}
