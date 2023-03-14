package com.kakaopay.cardPayment.service;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.common.util.ParsingUtil;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.payload.PayloadRequest;
import com.kakaopay.cardPayment.dto.payment.PaymentResponse;
import com.kakaopay.cardPayment.entity.Payload;
import com.kakaopay.cardPayment.entity.Payment;
import com.kakaopay.cardPayment.repository.PayloadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PayloadService {
    private PayloadRepository payloadRepository;

    @Transactional
    public void doPayload(Payment payment, String cardData) throws CustomException {
        try {
            Payload payload = ParsingUtil.toPayload(payment, cardData);
            payloadRepository.save(payload);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAILED_SAVE_PAYLOAD_DATA);
        }
    }


}
