package com.kcs.stepstory.security.handler.signin;

import com.kcs.stepstory.dto.response.JwtTokenDto;
import com.kcs.stepstory.repository.UserRepository;
import com.kcs.stepstory.security.info.AuthenticationResponse;
import com.kcs.stepstory.security.info.UserPrincipal;
import com.kcs.stepstory.utility.CookieUtil;
import com.kcs.stepstory.utility.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(principal.getUserId(), principal.getRole());

        userRepository.updateRefreshTokenAndLoginStatus(principal.getUserId(), jwtTokenDto.refreshToken(), true);

        AuthenticationResponse.makeLoginSuccessResponse(response, jwtTokenDto, jwtUtil.getRefreshExpiration());

        response.sendRedirect("http://localhost:3000");
    }
}