package com.yoonhyeok.cardPayment.common.util;

import com.yoonhyeok.cardPayment.common.constant.PaymentType;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import com.yoonhyeok.cardPayment.entity.Payload;
import com.yoonhyeok.cardPayment.entity.Payment;

/**
 * 각 객체 변환을 위한 Parsing 클래스
 * @author cyh68
 * @since 2023-03-23
 **/
public class ParsingUtil {
    
    /**
     * 결제 Payment Entity 변환 메소드
     * @param request {@link PaymentRequest}
     * @param cardInfo {@link String}
     * @return Payment {@link Payment}
     * @author cyh68
     * @since 2023-03-23
     **/
    public static Payment toPayment(PaymentRequest request, String cardInfo) {
        return Payment.builder()
                .id(DataHandlerUtil.generateUniqueId())
                .type(PaymentType.PAYMENT.getValue())
                .price(request.getPrice())
                .vat(DataHandlerUtil.calculateAutoVat(request.getVat(), request.getPrice()))
                .installMonth(request.getInstallMonth())
                .cardInfo(cardInfo)
                .build();
    }

    /**
     * 결제 취소 Payment Entity 변환 메소드
     * @param exPayment {@link Payment}
     * @param request {@link CancelRequest}
     * @return Payment {@link Payment}
     * @author cyh68
     * @since 2023-03-23
     **/
    public static Payment toCancel(Payment exPayment, CancelRequest request) {
        return Payment.builder()
                .id(DataHandlerUtil.generateUniqueId())
                .type(PaymentType.CANCEL.getValue())
                .price(request.getCancelPrice())
                .vat(request.getVat())
                .installMonth(0L) //취소시 할부개월수는 0
                .cardInfo(exPayment.getCardInfo())
                .paymentId(exPayment)
                .build();
    }

    /**
     * 변경된 결제 정보 업데이트 Payment Entity 변환 메소드
     * @param exPayment {@link Payment}
     * @param price {@link Long}
     * @param vat {@link Long}
     * @return Payment {@link Payment}
     * @author cyh68
     * @since 2023-03-23
     **/
    public static Payment toUpdatePayment(Payment exPayment, Long price, Long vat) {
        return Payment.builder()
                .id(exPayment.getId())
                .type(exPayment.getType())
                .price(price)
                .vat(vat)
                .installMonth(exPayment.getInstallMonth())
                .cardInfo(exPayment.getCardInfo())
                .build();
    }

    /**
     * 카드사 데이터 Payload Entity 변환 메소드
     * @param payment {@link Payment}
     * @param cardData {@link String}
     * @return Payload {@link Payload}
     * @author cyh68
     * @since 2023-03-23
     **/
    public static Payload toPayload(Payment payment, String cardData) {
        return Payload.builder()
                .data(cardData)
                .build();
    }
}
