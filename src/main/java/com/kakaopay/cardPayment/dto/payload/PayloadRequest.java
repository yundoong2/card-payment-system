package com.kakaopay.cardPayment.dto.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayloadRequest {
    private String id;
    private String cardData;
}
