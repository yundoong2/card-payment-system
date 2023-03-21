package com.kakaopay.cardPayment.service;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.common.util.ParsingUtil;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.BaseResponse;
import com.kakaopay.cardPayment.dto.payload.PayloadRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentResponse;
import com.kakaopay.cardPayment.entity.Payload;
import com.kakaopay.cardPayment.entity.Payment;
import com.kakaopay.cardPayment.repository.PayloadRepository;
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
     * @throws CustomException
     * @author cyh68
     * @since 2023-03-18
     */
    @Transactional
    public void doPayload(Payment payment, String cardData) throws CustomException {
        try {
            Payload payload = ParsingUtil.toPayload(payment, cardData);
            payloadRepository.save(payload);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAILED_SAVE_PAYLOAD_DATA, e.getLocalizedMessage());
        }
    }


}
