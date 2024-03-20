package com.kcs.stepstory.service;

import com.kcs.stepstory.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.request.UserUpdateDto;
import com.kcs.stepstory.dto.response.UserDetailDto;
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

        String profileImageName = null;
        if (imgFile != null && !imgFile.isEmpty()) {
            profileImageName = imageUtil.uploadProfileImageFile(imgFile, userId);
        }

        // 이미지 파일과 닉네임이 모두 제공된 경우
        if (profileImageName != null && requestDto != null) {
            user.updateInfo(requestDto, profileImageName);
        }
        // 닉네임만 제공된 경우
        else if (requestDto != null) {
            user.updateInfoOnlyMessage(requestDto);
        }
        // 이미지 파일만 제공된 경우
        else if (profileImageName != null) {
            user.updateInfoOnlyImage(profileImageName);
        }
        // 아무것도 제공되지 않은 경우
        else {
            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
    }

    public boolean checkDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
