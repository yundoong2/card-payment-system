package com.kakaopay.cardPayment.common.util;

import com.kakaopay.cardPayment.common.constant.Constants;
import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.common.constant.FieldInfo;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.payment.CancelRequest;
import com.kakaopay.cardPayment.dto.payment.CardInfoResponse;
import com.kakaopay.cardPayment.entity.Payment;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class DataHandlerUtil {

    public static String generateUniqueId() {
        return RandomStringUtils.random(20, "0123456789abcdefghigklmnoprstuvwxyzABCDEFGHIGKLMNOPRSTUVWXYZ");
    }

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
            throw new CustomException(ErrorCode.FAILED_GENERATE_PAYLOAD_DATA, e.getLocalizedMessage());
        }
    }

    public static Long getVat(Long vat, Long price) {
        if (price != null) {
            return (vat != null) ? vat : Math.round(price / 11);
        } else {
            return vat;
        }
    }

    public static Long getCancelVat(CancelRequest cancelRequest, Long remainPrice, Long remainVat) {
        if(cancelRequest.getVat() == null && remainPrice == 0) {
            return remainVat;
        } else {
            return getVat(cancelRequest.getVat(), cancelRequest.getCancelPrice());
        }
    }

    public static String getMaskCardNum(String cardNum) {
        int start = 6;
        int end = 3;

        return new StringBuilder()
                .append(StringUtils.substring(cardNum, 0, 6))
                .append(StringUtils.repeat(Constants.MASKING_CHAR, cardNum.length() - (start + end)))
                .append(StringUtils.substring(cardNum, cardNum.length() - end))
                .toString();
    }

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
                throw new CustomException(ErrorCode.INVALID_FORMAT_TYPE);
        }
    }
}
