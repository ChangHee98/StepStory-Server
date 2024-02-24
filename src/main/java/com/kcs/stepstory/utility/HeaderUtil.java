package org.donnguk.emodiary.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.donnguk.emodiary.contrant.Constants;
import org.donnguk.emodiary.dto.type.ErrorCode;
import org.donnguk.emodiary.exception.CommonException;
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
