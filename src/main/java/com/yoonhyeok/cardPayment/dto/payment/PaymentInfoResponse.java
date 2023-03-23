package com.yoonhyeok.cardPayment.dto.payment;

import com.yoonhyeok.cardPayment.common.constant.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * PaymentInfoResponse
 * - 결제 정보 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PaymentInfoResponse implements Serializable {
    private String id;
    private CardInfoResponse cardInfo;
    private PaymentType paymentType;
    private PriceInfoResponse priceInfo;
}
