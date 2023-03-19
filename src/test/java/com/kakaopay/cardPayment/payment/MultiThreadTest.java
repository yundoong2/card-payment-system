package com.kakaopay.cardPayment.payment;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.payment.CancelRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentResponse;
import com.kakaopay.cardPayment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MultiThreadTest extends CommonTestCase{

    @Autowired
    PaymentService paymentService;

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

                if (result instanceof CustomException) {
                    CustomException errorResponse = (CustomException) result;

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

                if (result instanceof CustomException) {
                    CustomException errorResponse = (CustomException) result;

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
