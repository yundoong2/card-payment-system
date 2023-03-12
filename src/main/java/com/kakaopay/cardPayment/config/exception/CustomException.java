package com.kakaopay.cardPayment.config.exception;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import lombok.Getter;

public class CustomException extends RuntimeException{

    @Getter
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
