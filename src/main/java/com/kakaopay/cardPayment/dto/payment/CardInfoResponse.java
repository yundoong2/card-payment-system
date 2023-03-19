package com.kakaopay.cardPayment.dto.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CardInfoResponse
 * - 카드 정보 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class CardInfoResponse {
    private String cardNo;
    private Long expiryDate;
    private Long cvc;
}
