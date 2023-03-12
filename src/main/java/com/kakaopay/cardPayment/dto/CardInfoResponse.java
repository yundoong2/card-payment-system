package com.kakaopay.cardPayment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfoResponse {
    private Long cardNo;
    private Long expiryDate;
    private Long cvc;
}
