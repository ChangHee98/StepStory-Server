package com.kcs.stepstory.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.dto.type.ErrorCode;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
