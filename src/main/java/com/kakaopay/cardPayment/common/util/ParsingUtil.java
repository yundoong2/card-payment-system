package com.kakaopay.cardPayment.common.util;

import com.kakaopay.cardPayment.common.constant.PaymentType;
import com.kakaopay.cardPayment.dto.payment.CancelRequest;
import com.kakaopay.cardPayment.dto.payment.CardInfoResponse;
import com.kakaopay.cardPayment.dto.payment.PaymentRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentResponse;
import com.kakaopay.cardPayment.entity.Payload;
import com.kakaopay.cardPayment.entity.Payment;

public class ParsingUtil {
    public static Payment toPayment(PaymentRequest request, String cardInfo) {
        Payment payment = Payment.builder()
                .id(DataHandlerUtil.generateUniqueId())
                .type(PaymentType.PAYMENT.getValue())
                .price(request.getPrice())
                .vat(DataHandlerUtil.getVat(request.getVat(), request.getPrice()))
                .installMonth(request.getInstallMonth())
                .cardInfo(cardInfo)
                .build();

        return payment;
    }

    public static Payment toCancel(Payment exPayment, CancelRequest request) {
        Payment payment = Payment.builder()
                .id(DataHandlerUtil.generateUniqueId())
                .type(PaymentType.CANCEL.getValue())
                .price(request.getCancelPrice())
                .vat(request.getVat())
                .installMonth(0L) //취소시 할부개월수는 0
                .cardInfo(exPayment.getCardInfo())
                .paymentId(exPayment)
                .build();

        return payment;
    }

    public static Payment toUpdatePayment(Payment exPayment, Long price, Long vat) {
        Payment payment = Payment.builder()
                .id(exPayment.getId())
                .type(exPayment.getType())
                .price(price)
                .vat(vat)
                .installMonth(exPayment.getInstallMonth())
                .cardInfo(exPayment.getCardInfo())
                .build();

        return payment;
    }

    public static Payload toPayload(Payment payment, String cardData) {
        Payload payload = Payload.builder()
                .data(cardData)
                .build();

        return payload;
    }
}
