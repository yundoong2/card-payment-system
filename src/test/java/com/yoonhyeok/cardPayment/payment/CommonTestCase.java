package com.yoonhyeok.cardPayment.payment;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonhyeok.cardPayment.ApplicationTests;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.FindRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * CommonTestCase
 * - 테스트 케이스 공통 Case Setup
 * @author cyh68
 * @since 2023-03-19
 */
@SpringBootTest(classes = ApplicationTests.class)
@ActiveProfiles("{test}")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CommonTestCase {

    /**
     * getTestPayRequest
     * - 테스트를 위한 결제 요청 샘플 데이터
     * @return PaymentRequest {@link PaymentRequest}
     * @author cyh68
     * @since 2023-03-18
     */
    public PaymentRequest getTestPayRequest() {
        PaymentRequest request = new PaymentRequest();
        request.setCardNo(1234567890L);
        request.setExpiryDate("1225");
        request.setCvc(333L);
        request.setInstallMonth(0L);
        request.setPrice(11000L);
        request.setVat(1000L);

        return request;
    }

    /**
     * getTestCancelRequest
     * - 테스트를 위한 결제 취소 샘플 데이터
     * @return CancelRequest {@link CancelRequest}
     * @author cyh68
     * @since 2023-03-18
     */
    public CancelRequest getTestCancelRequest() {
        CancelRequest request = new CancelRequest();
        request.setId("");
        request.setCancelPrice(11000L);
        request.setVat(1000L);

        return request;
    }

    /**
     * getTestFindRequest
     * - 테스트를 위한 결제 조회 샘플 데이터
     * @return FindRequest {@link FindRequest}
     * @author cyh68
     * @since 2023-03-18
     */
    public FindRequest getTestFindRequest() {
        FindRequest request = new FindRequest();
        request.setId("");

        return request;
    }

//    /**
//     * doTest
//     * - 결제 테스트 메소드
//     * @param request {@link T}
//     * @param url {@link String}
//     * @return ResultActions {@link ResultActions}
//     * @throws Throwable
//     */
//    public <T> ResultActions doTest(T request, String url) throws Throwable {
//        //Given
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//
//        //When
//        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//                .post(url)
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request));
//
//        //Then
//        resultActions = mockMvc.perform(builder);
//        resultActions.andDo(print());
//
//        return resultActions;
//    }

}
