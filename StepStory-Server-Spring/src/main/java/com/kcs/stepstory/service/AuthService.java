package com.kcs.stepstory.service;

import com.kcs.stepstory.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.dto.request.AuthSignUpDto;
import com.kcs.stepstory.dto.response.JwtTokenDto;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.repository.UserRepository;
import com.kcs.stepstory.dto.request.OauthSignUpDto;
import com.kcs.stepstory.utility.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(AuthSignUpDto authSignUpDto) {
        userRepository.save(
                User.signUp(authSignUpDto, bCryptPasswordEncoder.encode(authSignUpDto.password()))
        );
    }
    @Transactional
    public void signUp(Long userId, OauthSignUpDto oauthSignUpDto){
        User oauthUser = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        oauthUser.register(oauthSignUpDto.nickname());
    }
    @Transactional
    public JwtTokenDto reissue(Long userId, String refreshToken) {
        User user = userRepository.findByUserIdAndRefreshTokenAndIsLogin(userId, refreshToken, true)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getUserId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }
}
