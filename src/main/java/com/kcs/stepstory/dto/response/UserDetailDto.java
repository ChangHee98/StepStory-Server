package org.donnguk.emodiary.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.donnguk.emodiary.domain.User;
import org.donnguk.emodiary.dto.type.EProvider;

@Builder
public record UserDetailDto(
        @Schema(description = "유저 ID", example = "1")
        @NotNull(message = "유저 ID가 없습니다.")
        Long id,

        @Schema(description = "닉네임", example = "동국이")
        @NotNull(message = "닉네임이 없습니다.")
        String nickname,

        @JsonProperty("phone_number")@Schema(description = "전화번호", example = "010-1234-5678")
        @NotNull(message = "전화번호가 없습니다.")
        String phoneNumber,

        @Schema(description = "로그인 제공자", example = "KAKAO, GOOGLE, APPLE, DEFAULT")
        @NotNull(message = "로그인 제공자가 없습니다.")
        EProvider provider,

        @JsonProperty("profile_image_url") @Schema(description = "프로필 이미지 URL", example = "https://emodiary.s3.ap-northeast-2.amazonaws.com/profile/1.png")
        @NotNull(message = "프로필 이미지가 없습니다.")
        String profileImageUrl
) {
        public static UserDetailDto fromEntity(User user) {
            return UserDetailDto.builder()
                    .id(user.getId())
                    .nickname(user.getNickname())
                    .phoneNumber(user.getPhoneNumber())
                    .provider(user.getProvider())
                    .profileImageUrl(user.getProfileImageName())
                    .build();
        }
}
