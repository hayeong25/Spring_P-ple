package com.api.pple.exception;

import com.api.pple.utils.ErrorCode;
import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private final ErrorCode errorCode;

    public ClientException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}