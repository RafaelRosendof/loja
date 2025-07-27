package com.SistemLoja.SistemaLoja.DAO;

import com.SistemLoja.SistemaLoja.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


// TODO, implement more specifc methods for payments

public interface PaymentsDAO extends JpaRepository<PaymentEntity, Integer> {

    // SELECT public_qrcode FROM payment WHERE id = :id
    PaymentEntity findQrCodeById(int id);

     // SELECT status FROM payment WHERE id = :id
    String findStatusById(int id);

    // SELECT payment_method FROM payment WHERE id = :id
    String findPaymentMethodById(int id);


}
