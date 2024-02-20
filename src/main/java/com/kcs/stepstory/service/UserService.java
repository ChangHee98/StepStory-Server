package org.donnguk.emodiary.service;

import lombok.RequiredArgsConstructor;
import org.donnguk.emodiary.domain.User;
import org.donnguk.emodiary.dto.request.AuthSignUpDto;
import org.donnguk.emodiary.dto.request.UserUpdateDto;
import org.donnguk.emodiary.dto.response.UserDetailDto;
import org.donnguk.emodiary.dto.type.ErrorCode;
import org.donnguk.emodiary.exception.CommonException;
import org.donnguk.emodiary.repository.UserRepository;
import org.donnguk.emodiary.utility.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageUtil imageUtil;

    public UserDetailDto readUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserDetailDto.fromEntity(user);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateDto requestDto, MultipartFile imgFile) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        String profileImageName = imageUtil.uploadImageFile(imgFile);

        user.updateInfo(requestDto.nickname(), requestDto.phoneNumber(), profileImageName);
    }
}
