package com.kakaopay.cardPayment.payment.controller;

import com.kakaopay.cardPayment.dto.payment.*;
import com.kakaopay.cardPayment.entity.Payment;
import com.kakaopay.cardPayment.payment.CommonTestCase;
import com.kakaopay.cardPayment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PaymentControllerFailTest
 * - 결제 통신에 대한 성공 케이스 테스트
 * @author cyh68
 * @since 2023-03-19
 */
class PaymentControllerTest extends CommonTestCase {

    @Autowired
    PaymentService paymentService;

    /**
     * 결제 성공 테스트
     * @throws Throwable
     * @author cyh68
     * @since 2023-03-18
     */
    @Test
    @DisplayName("결제 성공 테스트")
    void doPayment() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();

        //When
        var result = super.doTest(request, "/payment");

        //Then
        result.andExpect(status().is2xxSuccessful());
        result.andExpect(jsonPath("$.message").value("SUCCESS"));
        result.andExpect(jsonPath("$.data").value(PaymentResponse.class));
    }

    /**
     * 결제 취소 성공 테스트
     * @throws Throwable
     * @author cyh68
     * @since 2023-03-18
     */
    @Test
    @DisplayName("결제 취소 성공 테스트")
    void doCancel() throws Throwable {
        //Given
        PaymentResponse paymentResponse = paymentService.doPayment(super.getTestPayRequest());

        CancelRequest cancelRequest = super.getTestCancelRequest();
        cancelRequest.setId(paymentResponse.getId());

        //When
        var result = super.doTest(cancelRequest, "/cancel");

        //Then
        result.andExpect(status().is2xxSuccessful());
        result.andExpect(jsonPath("$.message").value("SUCCESS"));
        result.andExpect(jsonPath("$.data").value(PaymentResponse.class));
        result.andExpect(jsonPath("$.data.id").value(paymentResponse.getId()));
    }

    /**
     * 결제 조회 성공 테스트
     * @throws Throwable
     * @author cyh68
     * @since 2023-03-18
     */
    @Test
    @DisplayName("결제 정보 조회 성공 테스트")
    void doFind() throws Throwable {
        //Given
        PaymentResponse paymentResponse = paymentService.doPayment(super.getTestPayRequest());

        FindRequest request = super.getTestFindRequest();
        request.setId(paymentResponse.getId());

        //When
        var result = super.doTest(request, "/find");

        //Then
        result.andExpect(status().is2xxSuccessful());
        result.andExpect(jsonPath("$.message").value("SUCCESS"));
        result.andExpect(jsonPath("$.data").value(PaymentInfoResponse.class));
        result.andExpect(jsonPath("$.data.id").value(paymentResponse.getId()));
    }
}