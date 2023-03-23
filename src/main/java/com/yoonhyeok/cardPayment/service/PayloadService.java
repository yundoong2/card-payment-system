package com.yoonhyeok.cardPayment.service;

import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.common.util.ParsingUtil;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.entity.Payload;
import com.yoonhyeok.cardPayment.entity.Payment;
import com.yoonhyeok.cardPayment.repository.PayloadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * PayloadService
 * - 카드사 데이터 전송 Service Layer
 * @author cyh68
 * @since 2023-03-18
 */
@Service
@RequiredArgsConstructor
public class PayloadService {
    private final PayloadRepository payloadRepository;

    /**
     * 결제 데이터 카드사 전송 서비스
     * @param payment {@link Payment}
     * @param cardData {@link String}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    public void doPayload(Payment payment, String cardData) throws PaymentException {
        try {
            Payload payload = ParsingUtil.toPayload(payment, cardData);
            payloadRepository.save(payload);
        } catch (Exception e) {
            throw new PaymentException(ErrorCode.FAILED_SAVE_PAYLOAD_DATA, e.getLocalizedMessage());
        }
    }


}
