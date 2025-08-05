package com.SistemLoja.SistemaLoja.Service;

import com.SistemLoja.SistemaLoja.DAO.ProdutoDAO;
import com.SistemLoja.SistemaLoja.Aux.PaymentQr;
import com.SistemLoja.SistemaLoja.Entity.PaymentEntity;
import com.SistemLoja.SistemaLoja.DAO.PaymentsDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentsService {

    private PaymentsDAO paymentsDAO;
    private ProdutoDAO produtoDAO;
    private PaymentQr paymentQr;

    @Autowired
    public PaymentsService(ProdutoDAO produtoDAO, PaymentQr paymentQr, PaymentsDAO paymentsDAO) {
        this.produtoDAO = produtoDAO;
        this.paymentQr = paymentQr;
        this.paymentsDAO = paymentsDAO;
    }

    /*
     * Methods for 
     * Creating eth , btc and pix payments (save in the database)
     * Creating the url for latter send in post request
     * Handling payment notification and updates (The DAO should implement all of this methods)
     * 
     */

    public PaymentEntity createPaymentEntity(String status , String paymentMethod , BigDecimal amount , LocalDateTime created_at , LocalDateTime updated_at , String publicQr){
        PaymentEntity payment = new PaymentEntity();
        payment.setStatus(status);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount);
        payment.setCreatedAt(created_at);
        payment.setUpdatedAt(updated_at);
        payment.setPublicQrcode(publicQr);

        return payment;
    }

    /*
     * gonna need function for update the payment status,
     * update the ammount , 
     */

    public PaymentEntity updatePaymentEntity(PaymentEntity payment, String status, BigDecimal amount, LocalDateTime updatedAt) {
        payment.setStatus(status);
        payment.setAmount(amount);
        payment.setUpdatedAt(updatedAt);
        return payment;
    }

    

    public PaymentEntity createPayment(PaymentEntity payment) {
        return paymentsDAO.save(payment);
    }

    public PaymentEntity findPaymentById(int id) {
        return paymentsDAO.findById(id);
    }

    public void updatePayment(PaymentEntity payment) {
        paymentsDAO.save(payment);
    }

    public void deletePayment(int id) {
        paymentsDAO.deleteById(id);
    }

    public String getStatus(int id) {
        String payment = paymentsDAO.findStatusById(id);
        return payment != null ? payment : null;
    }

    public String getPaymentMethod(int id) {
        String paymentMethod = paymentsDAO.findPaymentMethodById(id);
        return paymentMethod != null ? paymentMethod : null;
    }

    public String getQrCode(int id) {
        String qrCode = paymentsDAO.findQrCodeById(id);
        return qrCode != null ? qrCode : null;
    }

    public BigDecimal getAmount(int id) {
        PaymentEntity payment = paymentsDAO.findById(id);
        return payment != null ? payment.getAmount() : null;
    }

    public LocalDateTime getCreatedAt(int id) {
        PaymentEntity payment = paymentsDAO.findById(id);
        return payment != null ? payment.getCreatedAt() : null;
    }

    public LocalDateTime getUpdatedAt(int id) {
        PaymentEntity payment = paymentsDAO.findById(id);
        return payment != null ? payment.getUpdatedAt() : null;
    }



}
