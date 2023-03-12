package com.kakaopay.cardPayment.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "CARD_PAYMENT_TB")
public class Payment {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "VAT")
    private Long vat;

    @Column(name = "INSTALL_MONTH")
    private Long installMonth;

    @Column(name = "ENCRYPTED_CARD_INFO")
    private String cardInfo;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment paymentId;

    @OneToMany(mappedBy = "paymentId")
    private Set<Payment> payments;

}
