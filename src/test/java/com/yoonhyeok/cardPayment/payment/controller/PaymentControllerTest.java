package com.yoonhyeok.cardPayment.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonhyeok.cardPayment.common.constant.Constants;
import com.yoonhyeok.cardPayment.dto.payment.*;
import com.yoonhyeok.cardPayment.payment.CommonTestCase;
import com.yoonhyeok.cardPayment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    ResultActions resultActions;

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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.PAY_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.message").value("SUCCESS"));
        resultActions.andExpect(jsonPath("$.data").isNotEmpty());
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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.CANCEL_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cancelRequest));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.message").value("SUCCESS"));
        resultActions.andExpect(jsonPath("$.data").isNotEmpty());
        resultActions.andExpect(jsonPath("$.data.id").isNotEmpty());
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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(Constants.FIND_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.message").value("SUCCESS"));
        resultActions.andExpect(jsonPath("$.data").isNotEmpty());
        resultActions.andExpect(jsonPath("$.data.id").value(paymentResponse.getId()));
    }
}