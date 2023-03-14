package com.kakaopay.cardPayment.common.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    UNEXPECTED_ERROR("E00", "Unexpected Error"),

    INVALID_PARAM_OMITTED("E01", "누락된 입력 값이 존재합니다."),
    INVALID_PARAM_FORMAT("E02", "형식에 맞지 않는 입력 값이 존재합니다."),
    INVALID_PARAM_RANGE("E03", "값의 허용 범위를 벗어났습니다"),

    PAYMENT_NOT_FOUND("E04", "결제 정보가 존재하지 않습니다"),

    INVALID_FORMAT_TYPE("E05", "유효하지 않은 포맷 형식입니다."),
    FAILED_GENERATE_PAYLOAD_DATA("E06", "Payload 데이터를 생성하는데 실패하였습니다. "),
    FAILED_SAVE_PAYLOAD_DATA("E07", "Payload 데이터를 저장하는데 실패하였습니다.");

    @Getter
    private final String code;
    @Getter
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
