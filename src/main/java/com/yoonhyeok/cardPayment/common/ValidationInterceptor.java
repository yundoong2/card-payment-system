package com.yoonhyeok.cardPayment.common;

import com.yoonhyeok.cardPayment.config.annotation.Validation;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller의 유효성 검증을 위한 Interceptor
 * @author cyh68
 * @since 2023-03-23
 */
@Slf4j
public class ValidationInterceptor implements HandlerInterceptor {
    
    /**
     * 컨트롤러 실행 전 Request 유효성 검증 메소드
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @return boolean {@link Boolean}
     * @author cyh68
     * @since 2023-03-23
     * @throws PaymentException
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Validation validation = hm.getMethodAnnotation(Validation.class);

        if(validation != null) {
            //request parameter 정보를 가져와 유효성 검증 추후 구현 예정
        }

        return true;
    }
}
