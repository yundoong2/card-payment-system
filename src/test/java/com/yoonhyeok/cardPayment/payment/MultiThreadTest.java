package com.yoonhyeok.cardPayment.payment;

import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentResponse;
import com.yoonhyeok.cardPayment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * MultiThread Test 클래스
 * @author cyh68
 * @since 2023-03-23
 **/
public class MultiThreadTest extends CommonTestCase{

    @Autowired
    PaymentService paymentService;

    /**
     * 동시 결제 테스트
     * @author cyh68
     * @since 2023-03-23
     **/
    @Test
    @DisplayName("하나의 카드 정보로 동시 결제 테스트")
    void doDuplicatePayTest() {
        //Given
        PaymentRequest request = super.getTestPayRequest();

        //When
        List<ErrorCode> errors = new ArrayList<>();
        Runnable thread = () -> {
            try {
                Object result = paymentService.doPayment(request);

                if (result instanceof PaymentException) {
                    PaymentException errorResponse = (PaymentException) result;

                    if (errorResponse.getErrorCode() == ErrorCode.LOCK_PAYMENT) {
                        errors.add(errorResponse.getErrorCode());
                    }
                }
            } catch (Throwable e) {
            }
        };
        CompletableFuture
                .allOf(CompletableFuture.runAsync(thread), CompletableFuture.runAsync(thread))
                .join();

        //Then
        assertNotEquals(errors.size(), 0);
    }

    /**
     * 동시 결제 취소 테스트
     * @author cyh68
     * @since 2023-03-23
     **/
    @Test
    @DisplayName("하나의 카드 정보로 동시 결제 취소 테스트")
    void doDuplicateCancelTest() {
        //Given
        PaymentResponse paymentResponse = paymentService.doPayment(super.getTestPayRequest());
        CancelRequest request = super.getTestCancelRequest();
        request.setId(paymentResponse.getId());

        //When
        List<ErrorCode> errors = new ArrayList<>();
        Runnable thread = () -> {
            try {
                Object result = paymentService.doCancel(request);

                if (result instanceof PaymentException) {
                    PaymentException errorResponse = (PaymentException) result;

                    if (errorResponse.getErrorCode() == ErrorCode.LOCK_CANCEL) {
                        errors.add(errorResponse.getErrorCode());
                    }
                }
            } catch (Throwable e) {
            }
        };
        CompletableFuture
                .allOf(CompletableFuture.runAsync(thread), CompletableFuture.runAsync(thread))
                .join();

        //Then
        assertNotEquals(errors.size(), 0);
    }
}
