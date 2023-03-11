package com.kakaopay.cardPayment.dto;

import com.kakaopay.cardPayment.common.constant.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class paymentInfoResponse {
    private String id;
    private CardInfoResponse cardInfo;
    private PaymentType paymentType;
    private PriceInfoResponse priceInfo;
}
