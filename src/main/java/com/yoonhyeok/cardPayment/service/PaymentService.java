package com.yoonhyeok.cardPayment.service;

import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.common.constant.PaymentType;
import com.yoonhyeok.cardPayment.provider.CryptoProvider;
import com.yoonhyeok.cardPayment.common.util.DataHandlerUtil;
import com.yoonhyeok.cardPayment.provider.LockProvider;
import com.yoonhyeok.cardPayment.common.util.ParsingUtil;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.payment.*;
import com.yoonhyeok.cardPayment.entity.Payment;
import com.yoonhyeok.cardPayment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * PaymentService
 * - 카드 결제 통신 Service Layer
 *
 * @author cyh68
 * @since 2023-03-18
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CryptoProvider cryptoUtils;
    private final PayloadService payloadService;
    private final LockProvider lockProvider;

    /**
     * 결제 서비스
     *
     * @param request {@link PaymentRequest}
     * @return PaymentResponse {@link PaymentResponse}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    public PaymentResponse doPayment(PaymentRequest request) throws PaymentException {
        //카드정보 암호화
        String encryptedCardInfo = cryptoUtils.doEncrypt(request);

        //중복 결제에 대한 분산락 처리
        if (!lockProvider.lock(encryptedCardInfo).tryLock()) {
            throw new PaymentException(ErrorCode.LOCK_PAYMENT);
        }

        try {

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

            return paymentResponse;

        } catch (Exception e) {
            throw new PaymentException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        } finally {
            lockProvider.unlock();
        }
    }

    /**
     * 결제 취소 서비스
     *
     * @param cancelRequest {@link CancelRequest}
     * @return PaymentResponse {@link PaymentResponse}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    public PaymentResponse doCancel(CancelRequest cancelRequest) throws PaymentException {

        //결제 정보 조회
        Payment payment = paymentRepository.findById(cancelRequest.getId())
                .orElseThrow(() -> new PaymentException(ErrorCode.PAYMENT_NOT_FOUND));

        //남은 결제금액 및 취소 금액 체크
        if (payment.getPrice() < cancelRequest.getCancelPrice()) {
            throw new PaymentException(ErrorCode.INVALID_CANCEL_PRICE);
        }

        Long remainPrice = payment.getPrice() - cancelRequest.getCancelPrice();
        //취소 VAT 계산
        Long cancelVat = DataHandlerUtil.getCancelVat(cancelRequest, remainPrice, payment.getVat());

        //취소 부가가치세가 남은 결제 부가가치세 보다 큰 경우 에러 처리
        if(payment.getVat() < cancelVat) {
            throw new PaymentException(ErrorCode.INVALID_VAT_CANCEL);
        }

        //기존 결제 정보에서 취소 금액 및 취소 Vat을 계산하여 남은 값 저장
        Long remainVat = payment.getVat() - cancelVat;

        //중복 결제에 대한 분산락 처리
        if (!lockProvider.lock(cancelRequest.getId()).tryLock()) {
            throw new PaymentException(ErrorCode.LOCK_CANCEL);
        }

        try {
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

            return cancelResponse;

        } catch (Exception e) {
            throw new PaymentException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        } finally {
            lockProvider.unlock();
        }
    }

    /**
     * 결제 조회 서비스
     *
     * @param request {@link FindRequest}
     * @return PaymentInfoResponse {@link PaymentInfoResponse}
     * @throws PaymentException
     * @author cyh68
     * @since 2023-03-18
     * @throws PaymentException
     */
    public PaymentInfoResponse doFind(FindRequest request) throws PaymentException {
        //결제 정보 조회
        Payment payment = paymentRepository.findById(request.getId())
                .orElseThrow(() -> new PaymentException(ErrorCode.PAYMENT_NOT_FOUND));

        //금액정보
        PriceInfoResponse priceInfo = new PriceInfoResponse();
        priceInfo.setPrice(payment.getPrice());
        priceInfo.setVat(payment.getVat());

        //카드정보
        CardInfoResponse cardInfo = cryptoUtils.doDecrypt(payment.getCardInfo());
        cardInfo.setCardNo(DataHandlerUtil.getMaskCardNum(cardInfo.getCardNo()));

        //결제 조회 Response 정보
        PaymentInfoResponse paymentInfo = new PaymentInfoResponse();
        paymentInfo.setId(payment.getId());
        paymentInfo.setPaymentType(PaymentType.valueOf(payment.getType()));
        paymentInfo.setCardInfo(cardInfo);
        paymentInfo.setPriceInfo(priceInfo);

        return paymentInfo;
    }
}
