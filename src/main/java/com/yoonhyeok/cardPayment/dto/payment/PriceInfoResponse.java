package com.yoonhyeok.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * PriceInfoResponse
 * 결제 금액 정보 Response DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PriceInfoResponse implements Serializable {
    private Long price;
    private Long vat;
}
