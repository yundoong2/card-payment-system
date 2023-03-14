package com.kakaopay.cardPayment.dto.payment;

import com.kakaopay.cardPayment.common.constant.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoResponse {
    private String id;
    private CardInfoResponse cardInfo;
    private PaymentType paymentType;
    private PriceInfoResponse priceInfo;
}
