package com.kakaopay.cardPayment.service;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.common.constant.PaymentType;
import com.kakaopay.cardPayment.common.util.CryptoUtil;
import com.kakaopay.cardPayment.common.util.DataHandlerUtil;
import com.kakaopay.cardPayment.common.util.ParsingUtil;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.*;
import com.kakaopay.cardPayment.dto.payload.PayloadRequest;
import com.kakaopay.cardPayment.dto.payment.*;
import com.kakaopay.cardPayment.entity.Payment;
import com.kakaopay.cardPayment.repository.PayloadRepository;
import com.kakaopay.cardPayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CryptoUtil cryptoUtils;
    private final PayloadService payloadService;

    @Transactional
    public BaseResponse doPayment(PaymentRequest request) throws CustomException {
        try {
            //카드정보 암호화
            String encryptedCardInfo = cryptoUtils.doEncrypt(request);

            //결제 정보 저장
            Payment payment = ParsingUtil.toPayment(request, encryptedCardInfo);
            paymentRepository.save(payment);

            //카드 정보(카드번호, CVC, 유효기간)
            CardInfoResponse cardInfoResponse = cryptoUtils.doDecrypt(encryptedCardInfo);
            //카드사 전송 데이터
            String cardData = DataHandlerUtil.generatePayloadData(payment, cardInfoResponse);
            //카드사 데이터 저장
            payloadService.doPayload(payment, cardData);

            //결제 Response 정보
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setId(payment.getId());
            paymentResponse.setCardData(cardData);

            return new BaseResponse().onSuccess(paymentResponse);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        } finally {

        }
    }

    @Transactional
    public BaseResponse doCancel(CancelRequest cancelRequest) throws CustomException {
        try {
            //결제 정보 조회
            Payment payment = paymentRepository.findById(cancelRequest.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));

            //취소 VAT 계산
            Long cancelVat = DataHandlerUtil.getCancelVat(payment, cancelRequest);
            
            //기존 결제 정보에서 취소 금액 및 취소 Vat을 계산하여 남은 값 저장
            Long remainPrice = payment.getPrice() - cancelRequest.getCancelPrice();
            Long remainVat = payment.getVat() - cancelVat;

            //변경된 Price, Vat 값을 업데이트
            Payment updatePayment = ParsingUtil.toUpdatePayment(payment, remainPrice, remainVat);
            paymentRepository.save(updatePayment);

            //결제취소 정보 저장
            cancelRequest.setVat(cancelVat);
            Payment cancelPayment = ParsingUtil.toCancel(payment, cancelRequest);
            paymentRepository.save(cancelPayment);

            //카드 정보(카드번호, CVC, 유효기간)
            CardInfoResponse cardInfoResponse = cryptoUtils.doDecrypt(payment.getCardInfo());
            //카드사 전송 데이터
            String cardData = DataHandlerUtil.generatePayloadData(payment, cardInfoResponse);
            //카드사 데이터 저장
            payloadService.doPayload(payment, cardData);

            //결제 취소 Response 정보
            PaymentResponse cancelResponse = new PaymentResponse();
            cancelResponse.setId(cancelPayment.getId());
            cancelResponse.setCardData(cardData);

            return new BaseResponse().onSuccess(cancelResponse);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        } finally {

        }
    }

    public BaseResponse doFind(FindRequest request) throws CustomException {
        //결제 정보 조회
        Payment payment = paymentRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));

        //금액정보
        PriceInfoResponse priceInfo = new PriceInfoResponse();
        priceInfo.setPrice(payment.getPrice());
        priceInfo.setVat(payment.getVat());

        //카드정보
        CardInfoResponse cardInfo = cryptoUtils.doDecrypt(payment.getCardInfo());

        //결제 조회 Response 정보
        PaymentInfoResponse paymentInfo = new PaymentInfoResponse();
        paymentInfo.setId(payment.getId());
        paymentInfo.setPaymentType(PaymentType.valueOf(payment.getType()));
        paymentInfo.setCardInfo(cardInfo);
        paymentInfo.setPriceInfo(priceInfo);

        return new BaseResponse().onSuccess(paymentInfo);
    }
}
