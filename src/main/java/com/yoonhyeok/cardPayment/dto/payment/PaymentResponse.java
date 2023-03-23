package com.yoonhyeok.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * PaymentResponse
 * 결제 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PaymentResponse implements Serializable {
    private String id;
    private String cardData;
}
