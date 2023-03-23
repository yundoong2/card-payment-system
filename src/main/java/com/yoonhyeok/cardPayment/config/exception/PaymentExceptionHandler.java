package com.yoonhyeok.cardPayment.config.exception;

import com.yoonhyeok.cardPayment.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception 발생 시 처리 핸들러 정의
 */
@Slf4j
@RestControllerAdvice
public class PaymentExceptionHandler {

    /**
     * handlerException
     * - PaymentException 발생 시 핸들링
     * @param e {@link PaymentException}
     * @return BaseResponse {@link BaseResponse}
     * @author cyh68
     * @since 2023-03-18
     */
    @ExceptionHandler(PaymentException.class)
    @ResponseBody
    public BaseResponse handlerException(PaymentException e) {

        return new BaseResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getErrorMessage());
    }
}
