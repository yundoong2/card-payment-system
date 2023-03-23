package com.yoonhyeok.cardPayment.repository;

import com.yoonhyeok.cardPayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PaymentRepository
 * - 카드 결제 데이터 저장을 위한 JPA Interface
 * @author cyh68
 * @since 2023-03-18
 */
public interface PaymentRepository extends JpaRepository<Payment, String> {

}
