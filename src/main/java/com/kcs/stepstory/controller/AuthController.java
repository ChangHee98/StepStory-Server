package org.donnguk.emodiary.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.donnguk.emodiary.annotation.UserId;
import org.donnguk.emodiary.constants.Constants;
import org.donnguk.emodiary.dto.common.ResponseDto;
import org.donnguk.emodiary.dto.request.AuthSignUpDto;
import org.donnguk.emodiary.dto.response.JwtTokenDto;
import org.donnguk.emodiary.dto.type.ErrorCode;
import org.donnguk.emodiary.exception.CommonException;
import org.donnguk.emodiary.service.AuthService;
import org.donnguk.emodiary.utility.CookieUtil;
import org.donnguk.emodiary.utility.HeaderUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(
            @RequestBody @Valid AuthSignUpDto authSignUpDto
            ) {
        authService.signUp(authSignUpDto);

        return ResponseDto.ok(null);
    }

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response,
            @UserId Long userId) {
        String refreshToken = StringUtils.startsWith(request.getHeader("User-Agent"), "Dart") ?
                HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                        .orElseThrow(() -> new CommonException(ErrorCode.MISSING_REQUEST_HEADER))
                : CookieUtil.refineCookie(request, "refresh_token")
                        .orElseThrow(() -> new CommonException(ErrorCode.MISSING_REQUEST_HEADER));

        JwtTokenDto jwtTokenDto = authService.reissue(userId, refreshToken);

        if (request.getHeader("User-Agent") != null) {
            CookieUtil.addSecureCookie(response, "refresh_token", jwtTokenDto.refreshToken(), 60 * 60 * 24 * 14);
            jwtTokenDto = JwtTokenDto.of(jwtTokenDto.accessToken(), null);
        }

        return ResponseDto.ok(jwtTokenDto);
    }
}
