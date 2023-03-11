package com.kakaopay.cardPayment.config;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public BaseResponse handlerException(CustomException e) {

        return new BaseResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }
}
