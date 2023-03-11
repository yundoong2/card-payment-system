package com.kakaopay.cardPayment.repository;

import com.kakaopay.cardPayment.entity.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayloadRepository extends JpaRepository<Payload, String> {

}
