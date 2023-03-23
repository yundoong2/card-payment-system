package com.yoonhyeok.cardPayment.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoonhyeok.cardPayment.common.constant.Constants;
import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.FindRequest;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * ValidationAspect 클래스
 * - Service Layer의 유효성 검증을 위한 Aspect
 * @author cyh68
 * @since 2023-03-18
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class ValidationAspect {

    private final ObjectMapper objectMapper;

    /**
     * validatePayment
     * - doPayment 서비스 검증
     * @throws PaymentException
     * @param joinPoint {@link JoinPoint}
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    @Before(value = "execution(* com.kakaopay.cardPayment.service.PaymentService*.doPayment(..))")
    public void validatePayment(JoinPoint joinPoint) throws PaymentException {
        log.info("enter validatePayment");
        PaymentRequest param = objectMapper.convertValue(joinPoint.getArgs()[0], new TypeReference<PaymentRequest>() {
        });

        //필수 값 체크
        if (param == null || param.getCardNo() == null || param.getExpiryDate() == null
                || param.getCvc() == null || param.getInstallMonth() == null || param.getPrice() == null) {
            throw new PaymentException(ErrorCode.INVALID_PARAM_OMITTED);
        }

        //값 형식 체크
        if (!Pattern.matches(Constants.CARD_NUM_REGEX, param.getCardNo().toString())
                || !Constants.isValidExpiryDate(param.getExpiryDate())
                || !Pattern.matches(Constants.CVC_REGEX, param.getCvc().toString())
                || !Pattern.matches(Constants.INSTALL_REGEX, param.getInstallMonth().toString())
                || !Constants.isValidPrice(param.getPrice())) {

            throw new PaymentException(ErrorCode.INVALID_FORMAT_TYPE);
        }

        //부가가치세 & 결제 금액 체크
        if (param.getVat() != null) {
            if (param.getVat() > param.getPrice()) {
                throw new PaymentException(ErrorCode.INVALID_VAT_PRICE);
            }
        }
    }

    /**
     * validateCancel
     * - doCancel 서비스 검증
     * @throws PaymentException
     * @param joinPoint {@link JoinPoint}
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    @Before(value = "execution(* com.kakaopay.cardPayment.service.PaymentService*.doCancel(..))")
    public void validateCancel(JoinPoint joinPoint) {
        log.info("enter validateCancel");
        CancelRequest param = objectMapper.convertValue(joinPoint.getArgs()[0], new TypeReference<CancelRequest>() {
        });

        //필수 값 체크
        if (param == null || param.getId() == null || param.getCancelPrice() == null) {
            throw new PaymentException(ErrorCode.INVALID_PARAM_OMITTED);
        }

        //관리번호 유효성 체크
        if (!Pattern.matches(Constants.ID_REGEX, param.getId())) {
            throw new PaymentException(ErrorCode.INVALID_FORMAT_TYPE);
        }

        //부가가치세 & 결제 금액 체크
        if (param.getVat() != null) {
            if (param.getVat() > param.getCancelPrice()) {
                throw new PaymentException(ErrorCode.INVALID_VAT_PRICE);
            }
        }
    }

    /**
     * validateFind
     * - doFind 서비스 검증
     * @throws PaymentException
     * @param joinPoint {@link JoinPoint}
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    @Before(value = "execution(* com.kakaopay.cardPayment.service.PaymentService*.doFind(..))")
    public void validateFind(JoinPoint joinPoint) {
        log.info("enter validateFind");
        FindRequest param = objectMapper.convertValue(joinPoint.getArgs()[0], new TypeReference<FindRequest>() {
        });

        //필수 값 체크
        if(param == null || param.getId() == null) {
            throw new PaymentException(ErrorCode.INVALID_PARAM_OMITTED);
        }

        //관리번호 유효성 체크
        if (!Pattern.matches(Constants.ID_REGEX, param.getId())) {
            throw new PaymentException(ErrorCode.INVALID_FORMAT_TYPE);
        }
    }
}
