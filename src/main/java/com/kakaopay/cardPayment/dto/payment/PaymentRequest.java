package com.kakaopay.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long cardNo;
    private Long expiryDate;
    private Long cvc;
    private Long installMonth;
    private Long price;
    private Long vat;
}
