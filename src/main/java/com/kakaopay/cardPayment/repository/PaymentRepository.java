package com.kakaopay.cardPayment.repository;

import com.kakaopay.cardPayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
