package com.kakaopay.cardPayment.common.constant;

import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public enum  PaymentType {
    PAYMENT("PAYMENT"),
    CANCEL("CANCEL");

    private String value;

    PaymentType(String value) {
        this.value = value;
    }
}
