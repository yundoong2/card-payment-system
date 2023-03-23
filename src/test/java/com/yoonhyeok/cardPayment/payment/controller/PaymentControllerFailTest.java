package com.yoonhyeok.cardPayment.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonhyeok.cardPayment.common.constant.Constants;
import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.FindRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import com.yoonhyeok.cardPayment.payment.CommonTestCase;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * PaymentControllerFailTest
 * - 결제 통신에 대한 실패 케이스 테스트
 * @author cyh68
 * @since 2023-03-19
 */
public class PaymentControllerFailTest extends CommonTestCase {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    ResultActions resultActions;
    @Test
    @DisplayName("결제 정보 누락")
    void 결제정보_누락() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setCardNo(null);
        request.setExpiryDate(null);
        request.setCvc(null);
        request.setInstallMonth(null);
        request.setPrice(null);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAM_OMITTED.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PARAM_OMITTED.getMessage()));
    }

    @Test
    @DisplayName("카드번호 범위 미만")
    void 카드번호_범위_미만() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setCardNo(12345678L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("카드번호 범위 초과")
    void 카드번호_범위_초과() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setCardNo(12345678901234567L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("유효기간 범위 미만")
    void 유효기간_범위_미만() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setExpiryDate("123");

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("유효기간 범위 초과")
    void 유효기간_범위_초과() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setExpiryDate("12345");

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("유효기간 유효하지 않음")
    void 유효기간_유효하지_않음() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setExpiryDate("1323");

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("cvc 범위 미만")
    void cvc_범위_미만() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setCvc(44L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("cvc 범위 초과")
    void cvc_범위_초과() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setCvc(4444L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("할부개월수 범위 미만")
    void 할부개월수_범위_미만() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setInstallMonth(-1L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("할부개월수 범위 초과")
    void 할부개월수_범위_초과() throws Throwable {
        //given
        PaymentRequest request = super.getTestPayRequest();
        request.setInstallMonth(20L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("결제금액 범위 미만")
    void 결제금액_범위_미만() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setPrice(99L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("결제금액 범위 초과")
    void 결제금액_범위_초과() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setPrice(1000000001L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("부가가치세가 결제 금액보다 큼")
    void 부가가치세가_결제_금액보다_큼() throws Throwable {
        //Given
        PaymentRequest request = super.getTestPayRequest();
        request.setPrice(1000L);
        request.setVat(2000L);

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
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_VAT_PRICE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_VAT_PRICE.getMessage()));
    }

    @Test
    @DisplayName("결제 취소 정보 누락")
    void 결제취소_정보_누락() throws Throwable {
        //given
        CancelRequest request = super.getTestCancelRequest();
        request.setId(null);
        request.setCancelPrice(null);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.CANCEL_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAM_OMITTED.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PARAM_OMITTED.getMessage()));
    }

    @Test
    @DisplayName("결제 취소 관리번호 유효하지 않음")
    void 결제취소_관리번호_유효하지_않음() throws Throwable {
        //Given
        CancelRequest request = super.getTestCancelRequest();
        request.setId("abcd1234");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.CANCEL_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("부가가치세가 취소 금액보다 큼")
    void 부가가치세가_취소_금액보다_큼() throws Throwable {
        //Given
        CancelRequest request = super.getTestCancelRequest();
        request.setCancelPrice(1000L);
        request.setVat(1500L);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.CANCEL_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }

    @Test
    @DisplayName("결제조회 정보 누락")
    void 결제조회_정보_누락() throws Throwable {
        //Given
        FindRequest request = super.getTestFindRequest();
        request.setId(null);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.FIND_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_PARAM_OMITTED.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PARAM_OMITTED.getMessage()));
    }

    @Test
    @DisplayName("결제조회 관리번호 유효하지 않음")
    void 결제조회_관리번호_유효하지_않음() throws Throwable {
        //Given
        FindRequest request = super.getTestFindRequest();
        request.setId("abcd12345");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        //When
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post(Constants.FIND_URL)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        //Then
        resultActions = mockMvc.perform(builder);
        resultActions.andDo(print());
        resultActions.andExpect(jsonPath("$.code").value(ErrorCode.INVALID_FORMAT_TYPE.getCode()));
        resultActions.andExpect(jsonPath("$.message").value(ErrorCode.INVALID_FORMAT_TYPE.getMessage()));
    }
}
