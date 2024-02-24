package com.kcs.stepstory.dto.reqeust;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "AuthSignUpDto", description = "회원가입 요청")
public record AuthSignUpDto(
        @JsonProperty("serial_id") @Schema(description = "시리얼 ID", example = "dongguk")
        @Size(min = 4, max = 20, message = "시리얼 ID는 4~20자리로 입력해주세요.")
        @NotNull(message = "serial_id는 null이 될 수 없습니다.")
        String serialId,

        @JsonProperty("password") @Schema(description = "비밀번호", example = "1234567890")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%]).{10,20}$",
                message = "비밀번호는 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 특수문자(!, @, #, %, $) 1개 이상으로 구성된 10~20자리 비밀번호로 입력해주세요.")
        @NotNull(message = "password는 null이 될 수 없습니다.")
        String password,

        @Schema(description = "닉네임", example = "동국이")
        @Size(min = 2, max = 10, message = "닉네임은 2~10자리로 입력해주세요.")
        @NotNull(message = "provider는 null이 될 수 없습니다.")
        String nickname,
        @JsonProperty("phone_number") @Schema(description = "전화번호", example = "010-1234-5678")
        @Pattern(
                regexp = "^\\d{3}-\\d{4}-\\d{4}$",
                message = "전화번호 형식(000-0000-0000)이 올바르지 않습니다.")
        @NotNull(message = "phone_number는 null이 될 수 없습니다.")
        String phoneNumber
) {
}
