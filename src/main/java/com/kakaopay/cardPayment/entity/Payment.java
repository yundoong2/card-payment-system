package com.kakaopay.cardPayment.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Payment
 * 카드 결제 Entity
 * @author cyh68
 * @since 2023-03-18
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "CARD_PAYMENT_TB")
public class Payment {
    //결제 관리 번호
    @Id
    @Column(name = "ID")
    private String id;

    //결제 타입(결제, 취소)
    @Column(name = "TYPE")
    private String type;

    //결제(취소) 금액
    @Column(name = "PRICE")
    private Long price;

    //부가가치세
    @Column(name = "VAT")
    private Long vat;

    //할부 개월수
    @Column(name = "INSTALL_MONTH")
    private Long installMonth;

    //카드 정보
    @Column(name = "ENCRYPTED_CARD_INFO")
    private String cardInfo;

    //결제 관리번호(취소 데이터 연결 용도)
    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment paymentId;

    @OneToMany(mappedBy = "paymentId")
    private Set<Payment> payments;

}
