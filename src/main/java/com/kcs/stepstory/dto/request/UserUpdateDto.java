package com.kcs.stepstory.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "UserUpdateDto", description = "유저 정보 수정 요청")
public record UserUpdateDto(
        @Schema(description = "닉네임", example = "개똥이")
        @Size(min = 2, max = 10, message = "닉네임은 2~10자리로 입력해주세요.")
        @NotNull(message = "닉네임은 null이 될 수 없습니다.")
        String nickname,
        @JsonProperty("phone_number") @Schema(description = "전화번호", example = "010-1234-5678")
        @Pattern(
                regexp = "^\\d{3}-\\d{4}-\\d{4}$",
                message = "전화번호 형식(000-0000-0000)이 올바르지 않습니다.")
        String phoneNumber
) {
}
