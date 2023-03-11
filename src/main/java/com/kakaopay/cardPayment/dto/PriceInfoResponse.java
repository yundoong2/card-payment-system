package com.kakaopay.cardPayment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceInfoResponse {
    private Long price;
    private Long vat;
}
