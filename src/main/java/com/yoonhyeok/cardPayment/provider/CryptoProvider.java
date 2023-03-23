package com.yoonhyeok.cardPayment.provider;

import com.yoonhyeok.cardPayment.common.constant.ErrorCode;
import com.yoonhyeok.cardPayment.config.exception.PaymentException;
import com.yoonhyeok.cardPayment.dto.payment.CardInfoResponse;
import com.yoonhyeok.cardPayment.dto.payment.PaymentRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

/**
 * 카드 정보 암호화를 위한 클래스
 * @author cyh68
 * @since 2023-03-18
 */
@Component
public class CryptoProvider {
    @Value("${card.cipher.algorithm}")
    private String CIPHER_ALGORITHM;
    @Value("${card.cipher.secret-key}")
    private String CIPHER_KEY;
    @Value("${card.cipher.separator}")
    private String CIPHER_SEPARATOR;
    private SecretKey secretKey;
    private Cipher cipher;


    /**
     * Cipher 객체 초기화 메소드
     * @author cyh68
     * @since 2023-03-18
     * @exception PaymentException
     */
    @PostConstruct
    public void initCrypto() {
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            secretKey = SecretKeyFactory
                    .getInstance(CIPHER_ALGORITHM)
                    .generateSecret(new SecretKeySpec(CIPHER_KEY.getBytes(), CIPHER_ALGORITHM));
        } catch (Exception e) {
            throw new PaymentException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        }
    }

    /**
     * 암호화 메소드
     * @param request {@link PaymentRequest}
     * @return String {@link String}
     * @author cyh68
     * @since 2023-03-18
     * @exception PaymentException
     */
    public String doEncrypt(PaymentRequest request) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            String serializedData = StringUtils.join(
                    Arrays.asList(
                            request.getCardNo(),
                            request.getExpiryDate(),
                            request.getCvc()
                    ),
                    CIPHER_SEPARATOR
            );

            return Base64.getEncoder().encodeToString(cipher.doFinal(serializedData.getBytes()));
        } catch (Exception e) {
            throw new PaymentException(ErrorCode.ENCRYPT_FAILED, e.getLocalizedMessage());
        }
    }

    /**
     * 복호화 메소드
     * @param encryptedData
     * @return CardInfoResponse {@link CardInfoResponse}
     * @author cyh68
     * @since 2023-03-18
     * @exception PaymentException
     */
    public CardInfoResponse doDecrypt(String encryptedData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            String decryptedString = Optional
                    .of(cipher.doFinal(Base64.getDecoder().decode(encryptedData)))
                    .map(String::new)
                    .orElseThrow(() -> new PaymentException(ErrorCode.DECRYPT_FAILED));

            CardInfoResponse cardInfo = Optional
                    .of(StringUtils.split(decryptedString, CIPHER_SEPARATOR))
                    .map(args -> {
                        CardInfoResponse info = new CardInfoResponse();
                        info.setCardNo(args[0]);
                        info.setExpiryDate(Long.valueOf(args[1]));
                        info.setCvc(Long.valueOf(args[2]));
                        return info;
                    })
                    .orElseThrow(() -> new PaymentException(ErrorCode.CARD_INFO_FAILED));

            return cardInfo;
        } catch (PaymentException e) {
            throw new PaymentException(e.getErrorCode(), e.getLocalizedMessage());
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new PaymentException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        }
    }
}

