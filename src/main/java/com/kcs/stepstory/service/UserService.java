package com.kcs.stepstory.service;

import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.request.UserUpdateDto;
import com.kcs.stepstory.dto.response.UserDetailDto;
import com.kcs.stepstory.dto.type.ErrorCode;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.repository.UserRepository;
import com.kcs.stepstory.utility.ImageUtil;
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

        user.updateInfo(requestDto.nickname(), requestDto.selfIntro(), profileImageName);
    }

    public boolean checkDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
