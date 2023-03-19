package com.kakaopay.cardPayment.payment.controller;


import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.BaseResponse;
import com.kakaopay.cardPayment.dto.payment.*;
import com.kakaopay.cardPayment.service.PaymentService;
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
     * @throws CustomException
     * @author cyh68
     * @since 2023-03-18
     */
    @PostMapping("/payment")
    public @ResponseBody BaseResponse doPayment(@RequestBody PaymentRequest request) throws CustomException {
        PaymentResponse response = paymentService.doPayment(request);

        return new BaseResponse().onSuccess(response);
    }

    /**
     * 결제 취소 메소드
     * @param request {@link CancelRequest}
     * @return BaseResponse {@link BaseResponse}
     * @throws CustomException
     * @author cyh68
     * @since 2023-03-18
     */
    @PostMapping("/cancel")
    public @ResponseBody BaseResponse doCancel(@RequestBody CancelRequest request) throws CustomException {
        PaymentResponse response = paymentService.doCancel(request);

        return new BaseResponse().onSuccess(response);
    }

    /**
     * 결제 조회 메소드
     * @param request {@link FindRequest}
     * @return BaseResponse {@link BaseResponse}
     * @throws CustomException
     * @author cyh68
     * @since 2023-03-18
     */
    @PostMapping("/find")
    public @ResponseBody BaseResponse doFind(@RequestBody FindRequest request) throws CustomException {
        PaymentInfoResponse response = paymentService.doFind(request);

        return new BaseResponse().onSuccess(response);
    }
}
