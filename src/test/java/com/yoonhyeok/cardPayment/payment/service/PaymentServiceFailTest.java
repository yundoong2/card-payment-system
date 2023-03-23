package com.yoonhyeok.cardPayment.payment.service;

import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.FindRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import com.yoonhyeok.cardPayment.payment.CommonTestCase;
import com.yoonhyeok.cardPayment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentServiceFailTest extends CommonTestCase {

    @Autowired
    private PaymentService paymentService;

    @Test
    @DisplayName("결제 Service 실패 테스트")
    void doPaymentFailTest() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setCardNo(null);

        //When
        Throwable exception = assertThrows(PaymentException.class, () ->{
            paymentService.doPayment(request);
        });

        //Then
        PaymentException e = (PaymentException) exception;
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getCode(), e.getErrorCode().getCode());
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getMessage(), e.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("결제 취소 Service 실패 테스트")
    void doCancelFailTest() throws Throwable {
        //Given
        CancelRequest request = super.getTestCancelRequest();
        request.setId(null);

        //When
        Throwable exception = assertThrows(PaymentException.class, () ->{
            paymentService.doCancel(request);
        });

        //Then
        PaymentException e = (PaymentException) exception;
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getCode(), e.getErrorCode().getCode());
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getMessage(), e.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("결제 조회 Service 실패 테스트")
    void doFindFailTest() throws Throwable {
        //Given
        FindRequest request = super.getTestFindRequest();
        request.setId(null);

        //When
        Throwable exception = assertThrows(PaymentException.class, () ->{
            paymentService.doFind(request);
        });

        //Then
        PaymentException e = (PaymentException) exception;
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getCode(), e.getErrorCode().getCode());
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getMessage(), e.getErrorCode().getMessage());
    }
}
