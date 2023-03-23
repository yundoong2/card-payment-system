package com.yoonhyeok.cardPayment.config.exception;

import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import lombok.Getter;

/**
 * 비지니스 로직에 대한 별도 처리 Excpetion 정의
 * @author cyh68
 * @since 2023-03-23
 **/
public class PaymentException extends RuntimeException{

    @Getter
    private ErrorCode errorCode;
    @Getter
    private String errorMessage;

    public PaymentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public PaymentException(ErrorCode errorCode, String message) {
        this.errorMessage = message;
        this.errorCode = errorCode;
    }
}
