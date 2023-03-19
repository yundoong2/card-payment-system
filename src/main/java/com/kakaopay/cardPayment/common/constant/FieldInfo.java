package com.kakaopay.cardPayment.common.constant;

import lombok.Getter;

/**
 * 결제 정보 필드 형식 및 길이 정의
 */
public enum FieldInfo {
    DATA_LENGTH("NUMBER", 4),
    DATA_TYPE("STRING", 10),
    ID("STRING", 20),
    CARD_NUM("NUMBER_L", 20),
    INSTALL_MONTH("NUMBER_0", 2),
    EXPIRY_DATE("NUMBER_L", 4),
    CVC("NUMBER_L", 3),
    PRICE("NUMBER", 10),
    VAT("NUMBER_0", 10),
    PAYMENT_ID("STRING", 20),
    ENCRYPTED_CARD_INFO("STRING", 300),
    RESERVE_FIELD("STRING", 47);

    @Getter
    private final String type;
    @Getter
    private final int length;

    FieldInfo(String type, int length) {
        this.type = type;
        this.length = length;
    }
}
