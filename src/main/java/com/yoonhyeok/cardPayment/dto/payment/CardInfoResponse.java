package com.yoonhyeok.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * CardInfoResponse
 * - 카드 정보 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class CardInfoResponse implements Serializable {
    private String cardNo;
    private Long expiryDate;
    private Long cvc;
}
