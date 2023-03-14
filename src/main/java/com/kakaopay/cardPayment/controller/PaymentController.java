package com.kakaopay.cardPayment.controller;


import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.BaseResponse;
import com.kakaopay.cardPayment.dto.payment.CancelRequest;
import com.kakaopay.cardPayment.dto.payment.FindRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentRequest;
import com.kakaopay.cardPayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment")
    public @ResponseBody BaseResponse doPayment(@RequestBody PaymentRequest request) throws CustomException {
        BaseResponse response = paymentService.doPayment(request);

        return new BaseResponse().onSuccess(response);
    }

    @PutMapping("/payment")
    public @ResponseBody BaseResponse doCancel(@RequestBody CancelRequest request) throws CustomException {
        BaseResponse response = paymentService.doCancel(request);

        return new BaseResponse().onSuccess(response);
    }

    @GetMapping("/payment")
    public @ResponseBody BaseResponse doFind(@RequestBody FindRequest request) throws CustomException {
        BaseResponse response = paymentService.doFind(request);

        return new BaseResponse().onSuccess(response);
    }
}
