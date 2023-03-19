package com.kakaopay.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

/**
 * PaymentRequest
 * - 결제 Request DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PaymentRequest {
    //카드 번호
    private Long cardNo;
    //유효 기간
    private String expiryDate;
    //CVC
    private Long cvc;
    //할부 개월수
    private Long installMonth;
    //결제 금액
    private Long price;
    //부가가치세
    private Long vat;
}
