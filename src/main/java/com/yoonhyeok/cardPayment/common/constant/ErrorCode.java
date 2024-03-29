package com.yoonhyeok.cardPayment.common.constant;

import lombok.Getter;

/**
 * 에러 코드 정의
 * @author cyh68
 * @since 2023-03-18
 */
public enum ErrorCode {
    UNEXPECTED_ERROR("E00", "Unexpected Error"),

    INVALID_PARAM_OMITTED("E01", "누락된 입력 값이 존재합니다."),
    INVALID_VAT_CANCEL("E02", "취소 부가가치세는 남은 부가가치세보다 클 수 없습니다."),
    INVALID_VAT_PRICE("E03", "부가가치세는 결제 금액보다 클 수 없습니다."),
    INVALID_CANCEL_PRICE("E04", "취소 금액은 남은 결제 금액 보다 클 수 없습니다"),
    PAYMENT_NOT_FOUND("E05", "결제 정보가 존재하지 않습니다"),

    INVALID_FORMAT_TYPE("E06", "유효하지 않은 포맷 형식입니다."),
    FAILED_GENERATE_PAYLOAD_DATA("E07", "Payload 데이터를 생성하는데 실패하였습니다. "),
    FAILED_SAVE_PAYLOAD_DATA("E08", "Payload 데이터를 저장하는데 실패하였습니다."),

    LOCK_PAYMENT("E09", "이미 결제가 진행 중인 카드 정보입니다."),
    LOCK_CANCEL("E10", "이미 결제 취소가 진행 중인 카드 정보입니다."),

    ENCRYPT_FAILED("E11", "카드 정보 암호화에 실패했습니다."),
    DECRYPT_FAILED("E12", "카드 정보 복호화에 실패했습니다."),
    CARD_INFO_FAILED("E13", "카드 정보를 불러오는데 실패했습니다.");


    @Getter
    private final String code;
    @Getter
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
