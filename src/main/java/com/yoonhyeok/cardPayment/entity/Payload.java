package com.yoonhyeok.cardPayment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Payload
 * 카드사 데이터 Entity
 * @author cyh68
 * @since 2023-03-18
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYLOAD_TB")
public class Payload {
    @Id
    @Column(name = "DATA", length = 500)
    private String data;
}
