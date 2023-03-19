package com.kakaopay.cardPayment.dto.payment;

import com.kakaopay.cardPayment.common.constant.PaymentType;
import lombok.Getter;
import lombok.Setter;

/**
 * PaymentInfoResponse
 * - 결제 정보 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PaymentInfoResponse {
    private String id;
    private CardInfoResponse cardInfo;
    private PaymentType paymentType;
    private PriceInfoResponse priceInfo;
}
