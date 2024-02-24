package org.donnguk.emodiary.security.handler.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.donnguk.emodiary.dto.type.ErrorCode;
import org.donnguk.emodiary.security.info.AbstractAuthenticationFailure;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint extends AbstractAuthenticationFailure implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        ErrorCode errorCode = request.getAttribute("exception") == null ?
                ErrorCode.NOT_FOUND_END_POINT : (ErrorCode) request.getAttribute("exception");

        setErrorResponse(response, errorCode);
    }
}
