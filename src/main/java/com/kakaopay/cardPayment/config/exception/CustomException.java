package com.kakaopay.cardPayment.config.exception;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import lombok.Getter;

public class CustomException extends RuntimeException{

    @Getter
    private ErrorCode errorCode;
    @Getter
    private String errorMessage;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        this.errorMessage = message;
        this.errorCode = errorCode;
    }
}
