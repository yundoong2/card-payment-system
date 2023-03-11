package com.kakaopay.cardPayment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYLOAD_TB")
public class Payload {
    @Id
    @ManyToOne
    @JoinColumn(name = "ID")
    public Payment payment;

    @Column(name = "DATA")
    private String data;
}
