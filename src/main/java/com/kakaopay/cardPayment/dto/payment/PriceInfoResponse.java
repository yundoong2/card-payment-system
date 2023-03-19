package com.kakaopay.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

/**
 * PriceInfoResponse
 * 결제 금액 정보 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PriceInfoResponse {
    private Long price;
    private Long vat;
}
