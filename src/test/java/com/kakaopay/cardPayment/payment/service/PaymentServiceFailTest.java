package com.kakaopay.cardPayment.payment.service;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.payment.CancelRequest;
import com.kakaopay.cardPayment.dto.payment.FindRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentRequest;
import com.kakaopay.cardPayment.payment.CommonTestCase;
import com.kakaopay.cardPayment.service.PaymentService;
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
        Throwable exception = assertThrows(CustomException.class, () ->{
            paymentService.doPayment(request);
        });

        //Then
        CustomException e = (CustomException) exception;
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
        Throwable exception = assertThrows(CustomException.class, () ->{
            paymentService.doCancel(request);
        });

        //Then
        CustomException e = (CustomException) exception;
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
        Throwable exception = assertThrows(CustomException.class, () ->{
            paymentService.doFind(request);
        });

        //Then
        CustomException e = (CustomException) exception;
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getCode(), e.getErrorCode().getCode());
        assertEquals(ErrorCode.INVALID_PARAM_OMITTED.getMessage(), e.getErrorCode().getMessage());
    }
}
