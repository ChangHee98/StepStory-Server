package com.kcs.stepstory.security.handler.singout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.repository.UserRepository;
import com.kcs.stepstory.security.info.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomSignOutProcessHandler implements LogoutHandler {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        if (authentication == null) {
            return;
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), null, false);
    }
}
