package com.kakaopay.cardPayment.common.util;

import com.kakaopay.cardPayment.common.constant.PaymentType;
import com.kakaopay.cardPayment.dto.CardInfoResponse;
import com.kakaopay.cardPayment.dto.PaymentRequest;
import com.kakaopay.cardPayment.entity.Payment;

public class ParsingUtil {
    public static Payment toPaymentEntity(PaymentRequest request, String id, String cardInfo) {
        Payment payment = Payment.builder()
                .id(id)
                .type(PaymentType.PAYMENT.getValue())
                .price(request.getPrice())
                .vat(DataHandlerUtil.getVat(request.getVat(), request.getPrice()))
                .installMonth(request.getInstallMonth())
                .cardInfo(cardInfo)
                .build();

        return payment;
    }

    public static Payment toCancelEntity(Payment oriPayment, String id) {
        Payment payment = Payment.builder()
                .id(id)
                .type(PaymentType.PAYMENT.getValue())
                .price(oriPayment.getPrice())
                .vat(DataHandlerUtil.getVat(oriPayment.getVat(), oriPayment.getPrice()))
                .installMonth(oriPayment.getInstallMonth())
                .cardInfo(oriPayment.getCardInfo())
                .paymentId(oriPayment)
                .build();

        return payment;
    }
}
