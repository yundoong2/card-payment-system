package com.yoonhyeok.cardPayment.common.util;

import com.yoonhyeok.cardPayment.common.constant.Constants;
import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.common.constant.FieldInfo;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.payment.CancelRequest;
import com.yoonhyeok.cardPayment.dto.payment.CardInfoResponse;
import com.yoonhyeok.cardPayment.entity.Payment;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 카드 관련 데이터를 계산 또는 변환해주는 클래스
 * @author cyh68
 * @since 2023-03-18
 */
public class DataHandlerUtil {

    /**
     * 고유 관리번호 생성 메소드
     * @return String {@link String}
     * @author @cyh68
     * @since 2023-03-18
     */
    public static String generateUniqueId() {
        return RandomStringUtils.random(20, "0123456789abcdefghigklmnoprstuvwxyzABCDEFGHIGKLMNOPRSTUVWXYZ");
    }

    /**
     * 카드사 전송 데이터 조합 메소드
     * @param payment {@link Payment}
     * @param cardInfo {@link CardInfoResponse}
     * @return String {@link String}
     * @throws PaymentException
     */
    public static String generatePayloadData(Payment payment, CardInfoResponse cardInfo) {
        try {
            return new StringBuffer()
                    .append(onFormat(Constants.DATA_LENGTH, FieldInfo.DATA_LENGTH))
                    .append(onFormat(payment.getType(), FieldInfo.DATA_TYPE))
                    .append(onFormat(payment.getId(), FieldInfo.ID))
                    .append(onFormat(cardInfo.getCardNo(), FieldInfo.CARD_NUM))
                    .append(onFormat(payment.getInstallMonth(), FieldInfo.INSTALL_MONTH))
                    .append(onFormat(cardInfo.getExpiryDate(), FieldInfo.EXPIRY_DATE))
                    .append(onFormat(cardInfo.getCvc(), FieldInfo.CVC))
                    .append(onFormat(payment.getPrice(), FieldInfo.PRICE))
                    .append(onFormat(payment.getVat(), FieldInfo.VAT))
                    .append(onFormat((payment.getPaymentId() == null ? "" : payment.getPaymentId()), FieldInfo.PAYMENT_ID))
                    .append(onFormat(payment.getCardInfo(), FieldInfo.ENCRYPTED_CARD_INFO))
                    .append(onFormat("", FieldInfo.RESERVE_FIELD))
                    .toString();
        } catch (Exception e) {
            throw new PaymentException(ErrorCode.FAILED_GENERATE_PAYLOAD_DATA, e.getLocalizedMessage());
        }
    }

    /**
     * 부가가치세 자동 계산 메소드
     * @param vat {@link Long}
     * @param price {@link Long}
     * @return Long {@link Long}
     * @author cyh68
     * @since 2023-03-18
     */
    public static Long calculateAutoVat(Long vat, Long price) {
        if (price != null) {
            return (vat != null) ? vat : Math.round(price / 11);
        } else {
            return vat;
        }
    }

    /**
     * 결제 취소 시 부가가치세 계산
     * @param cancelRequest {@link CancelRequest}
     * @param remainPrice {@link Long}
     * @param remainVat {@link Long}
     * @return Long {@link Long}
     * @author cyh68
     * @since 2023-03-18
     */
    public static Long getCancelVat(CancelRequest cancelRequest, Long remainPrice, Long remainVat) {
        if(cancelRequest.getVat() == null && remainPrice == 0) {
            return remainVat;
        } else {
            return calculateAutoVat(cancelRequest.getVat(), cancelRequest.getCancelPrice());
        }
    }

    /**
     * 카드 번호 마스킹 처리 메소드
     * @param cardNum {@link String}
     * @return String {@link String}
     * @author cyh68
     * @since 2023-03-18
     */
    public static String getMaskCardNum(String cardNum) {
        int start = 6;
        int end = 3;

        return new StringBuilder()
                .append(StringUtils.substring(cardNum, 0, 6))
                .append(StringUtils.repeat(Constants.MASKING_CHAR, cardNum.length() - (start + end)))
                .append(StringUtils.substring(cardNum, cardNum.length() - end))
                .toString();
    }

    /**
     * 카드 관련 데이터를 형식에 맞게 변환 메소드
     * @param value {@link Object}
     * @param fieldInfo {@link FieldInfo}
     * @return String {@link String}
     * @author cyh68
     * @since 2023-03-23
     * @throws PaymentException
     **/
    public static String onFormat(Object value, FieldInfo fieldInfo) {
        switch (fieldInfo.getType()) {
            case "NUMBER":
                return StringUtils.leftPad(String.valueOf(value), fieldInfo.getLength(), " ");
            case "NUMBER_0":
                return StringUtils.leftPad(String.valueOf(value), fieldInfo.getLength(), "0");
            case "NUMBER_L":
            case "STRING":
                return StringUtils.rightPad(String.valueOf(value), fieldInfo.getLength(), " ");
            default:
                throw new PaymentException(ErrorCode.INVALID_FORMAT_TYPE);
        }
    }
}
