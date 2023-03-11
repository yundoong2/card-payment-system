package com.kakaopay.cardPayment.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("E00", "Internal Server Error"),

    INVALID_PARAM_OMITTED("E01", "누락된 입력 값이 존재합니다."),
    INVALID_PARAM_FORMAT("E02", "형식에 맞지 않는 입력 값이 존재합니다."),
    INVALID_PARAM_RANGE("E03", "값의 허용 범위를 벗어났습니다"),

    PAYMENT_NOT_FOUND("E04", "결제 정보가 존재하지 않습니다");


    @Getter
    private final String code;
    @Getter
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
