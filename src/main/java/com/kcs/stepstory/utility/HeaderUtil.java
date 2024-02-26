package com.kcs.stepstory.utility;

import jakarta.servlet.http.HttpServletRequest;
import com.kcs.stepstory.dto.type.ErrorCode;
import com.kcs.stepstory.exception.CommonException;
import org.springframework.util.StringUtils;

import java.util.Optional;


public class HeaderUtil {
    public static Optional<String> refineHeader(
            HttpServletRequest request,
            String header,
            String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken) || !unpreparedToken.startsWith(prefix)) {
            throw new CommonException(ErrorCode.INVALID_HEADER_ERROR);
        }

        return Optional.of(unpreparedToken.substring(prefix.length()));
    }
}
