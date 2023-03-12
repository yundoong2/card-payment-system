package com.kakaopay.cardPayment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelRequest {
    private String id;
    private String cancelPrice;
    private Long vat;
}
