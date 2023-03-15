package com.kakaopay.cardPayment.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CardInfoResponse {
    private String cardNo;
    private Long expiryDate;
    private Long cvc;
}
