package com.kakaopay.cardPayment.service;

import com.kakaopay.cardPayment.dto.BaseResponse;
import com.kakaopay.cardPayment.repository.PayloadRepository;
import com.kakaopay.cardPayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PayloadRepository payloadRepository;


}
