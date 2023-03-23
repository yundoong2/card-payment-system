package com.yoonhyeok.cardPayment.payment.service;

import com.yoonhyeok.cardPayment.dto.payment.*;
import com.yoonhyeok.cardPayment.payment.CommonTestCase;
import com.yoonhyeok.cardPayment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentServiceTest extends CommonTestCase {
    @Autowired
    private PaymentService paymentService;

    @Test
    @DisplayName("결제 Service 성공 테스트")
    void doPaymentTest() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();

        //When
        PaymentResponse response = paymentService.doPayment(request);

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getCardData());
    }

    @Test
    @DisplayName("결제 취소 Service 성공 테스트")
    void doCancelTest() throws Throwable {
        //Given
        PaymentResponse paymentResponse = paymentService.doPayment(super.getTestPayRequest());

        CancelRequest request = super.getTestCancelRequest();
        request.setId(paymentResponse.getId());

        //When
        PaymentResponse response = paymentService.doCancel(request);

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getCardData());
    }

    @Test
    @DisplayName("결제 조회 Service 성공 테스트")
    void doFindTest() throws Throwable {
        //Given
        PaymentResponse paymentResponse = paymentService.doPayment(super.getTestPayRequest());

        FindRequest request = super.getTestFindRequest();
        request.setId(paymentResponse.getId());

        //When
        PaymentInfoResponse response = paymentService.doFind(request);

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getCardInfo());
        assertNotNull(response.getPaymentType());
        assertNotNull(response.getPriceInfo());
    }
}
