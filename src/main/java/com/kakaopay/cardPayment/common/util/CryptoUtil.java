package com.kakaopay.cardPayment.common.util;

import com.kakaopay.cardPayment.common.constant.ErrorCode;
import com.kakaopay.cardPayment.config.exception.CustomException;
import com.kakaopay.cardPayment.dto.payment.CardInfoResponse;
import com.kakaopay.cardPayment.dto.payment.PaymentRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
public class CryptoUtil {
    @Value("${card.cipher.algorithm}")
    private String CIPHER_ALGORITHM;
    @Value("${card.cipher.secret-key}")
    private String CIPHER_KEY;
    @Value("${card.cipher.separator}")
    private String CIPHER_SEPARATOR;
    private SecretKey secretKey;
    private Cipher cipher;

    @PostConstruct
    public void initCrypto() throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            secretKey = SecretKeyFactory
                    .getInstance(CIPHER_ALGORITHM)
                    .generateSecret(new SecretKeySpec(CIPHER_KEY.getBytes(), CIPHER_ALGORITHM));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        }
    }

    public String doEncrypt(PaymentRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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
            throw new CustomException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        }
    }

    public CardInfoResponse doDecrypt(String encryptedData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            String decryptedString = Optional
                    .of(cipher.doFinal(Base64.getDecoder().decode(encryptedData)))
                    .map(String::new)
                    .orElse(null);

            CardInfoResponse cardInfo = Optional
                    .of(StringUtils.split(decryptedString, CIPHER_SEPARATOR))
                    .map(args -> {
                        CardInfoResponse info = new CardInfoResponse();
                        info.setCardNo(Long.valueOf(args[0]));
                        info.setExpiryDate(Long.valueOf(args[1]));
                        info.setCvc(Long.valueOf(args[2]));
                        return info;
                    })
                    .get();

            return cardInfo;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNEXPECTED_ERROR, e.getLocalizedMessage());
        }
    }
}

