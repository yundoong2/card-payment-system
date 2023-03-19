package com.kakaopay.cardPayment.config.exception;

import com.kakaopay.cardPayment.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * CustomException 핸들러 정의
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * handlerException
     * - CustomException 발생 시 핸들링
     * @param e {@link CustomException}
     * @return BaseResponse {@link BaseResponse}
     * @author cyh68
     * @since 2023-03-18
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public BaseResponse handlerException(CustomException e) {

        return new BaseResponse(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getErrorMessage());
    }
}
