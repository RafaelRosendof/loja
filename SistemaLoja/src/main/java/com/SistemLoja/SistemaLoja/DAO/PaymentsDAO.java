package com.SistemLoja.SistemaLoja.DAO;

import com.SistemLoja.SistemaLoja.Aux.PaymentQr;
import com.SistemLoja.SistemaLoja.Entity.PaymentEntity;



// test the add and other functions if this DAO is working

// TODO, implement more specifc methods for payments, now gonna implement the Services class 

public interface PaymentsDAO {

    PaymentEntity save(PaymentEntity payment);
    PaymentEntity findById(int id);
    boolean deleteById(int id);
    PaymentEntity updatePayment(PaymentEntity payment);


    // SELECT public_qrcode FROM payment WHERE id = :id
    String findQrCodeById(int id);

     // SELECT status FROM payment WHERE id = :id
    String findStatusById(int id);

    // SELECT payment_method FROM payment WHERE id = :id
    String findPaymentMethodById(int id);


}
