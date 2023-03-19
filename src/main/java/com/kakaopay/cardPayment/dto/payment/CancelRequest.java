package com.kakaopay.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

/**
 * CancelRequest
 * - 카드 결제 취소 Request DTO
 * @author cyh68
 * @since 2023-03-18
 */
@Getter
@Setter
public class CancelRequest {
    //관리 번호
    private String id;
    //취소 금액
    private Long cancelPrice;
    //부가가치세
    private Long vat;
}
