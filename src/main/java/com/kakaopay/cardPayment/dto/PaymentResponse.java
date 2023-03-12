package com.kakaopay.cardPayment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private String id;
    private String cardInfo;
}
