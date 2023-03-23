package com.yoonhyeok.cardPayment.payment.controller;


import com.yoonhyeok.cardPayment.config.annotation.Validation;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.BaseResponse;
import com.yoonhyeok.cardPayment.dto.payment.*;
import com.yoonhyeok.cardPayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PaymentController 설명
 * - 카드 결제 통신 REST API Controller Layer
 * @author cyh68
 * @since 2023-03-18
 **/
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * 결제 메소드
     * @param request {@link PaymentRequest}
     * @return BaseResponse {@link BaseResponse}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     */
    @Validation
    @PostMapping("/payment")
    public BaseResponse doPayment(@RequestBody PaymentRequest request) throws PaymentException {
        PaymentResponse response = paymentService.doPayment(request);

        return new BaseResponse().onSuccess(response);
    }

    /**
     * 결제 취소 메소드
     * @param request {@link CancelRequest}
     * @return BaseResponse {@link BaseResponse}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     */
    @Validation
    @PostMapping("/cancel")
    public BaseResponse doCancel(@RequestBody CancelRequest request) throws PaymentException {
        PaymentResponse response = paymentService.doCancel(request);

        return new BaseResponse().onSuccess(response);
    }

    /**
     * 결제 조회 메소드
     * @param request {@link FindRequest}
     * @return BaseResponse {@link BaseResponse}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     */
    @Validation
    @GetMapping("/find")
    public BaseResponse doFind(@RequestBody FindRequest request) throws PaymentException {
        PaymentInfoResponse response = paymentService.doFind(request);

        return new BaseResponse().onSuccess(response);
    }
}
