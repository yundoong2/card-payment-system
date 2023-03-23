package com.yoonhyeok.cardPayment.common.constant;

import lombok.Getter;

/**
 * 결제/취소 타입 enum
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
public enum  PaymentType {
    PAYMENT("PAYMENT"),
    CANCEL("CANCEL");

    private String value;

    PaymentType(String value) {
        this.value = value;
    }
}
