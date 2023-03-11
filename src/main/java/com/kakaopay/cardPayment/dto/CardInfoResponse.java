package com.kakaopay.cardPayment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfoResponse {
    private String maskCardNo;
    private Long expiryDate;
    private Long cvc;
}
