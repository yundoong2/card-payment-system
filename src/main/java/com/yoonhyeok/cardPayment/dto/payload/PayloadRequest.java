package com.yoonhyeok.cardPayment.dto.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * PayloadRequest
 * - 카드사 전송 데이터 Request DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class PayloadRequest {
    private String id;
    private String cardData;
}
