package com.kakaopay.cardPayment.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelRequest {
    private String id;
    private Long cancelPrice;
    private Long vat;
}
