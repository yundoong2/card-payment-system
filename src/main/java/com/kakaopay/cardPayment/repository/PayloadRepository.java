package com.kakaopay.cardPayment.repository;

import com.kakaopay.cardPayment.entity.Payload;
import com.kakaopay.cardPayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PayloadRepository
 * - 카드사 데이터 저장을 위한 JPA Interface
 * @author cyh68
 * @since 2023-03-18
 */
public interface PayloadRepository extends JpaRepository<Payload, String> {

}
