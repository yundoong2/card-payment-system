package com.kakaopay.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

/**
 * PaymentResponse
 * 결제 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PaymentResponse {
    private String id;
    private String cardData;
}
