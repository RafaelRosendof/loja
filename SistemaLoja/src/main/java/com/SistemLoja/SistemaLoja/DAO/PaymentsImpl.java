package com.SistemLoja.SistemaLoja.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SistemLoja.SistemaLoja.Entity.PaymentEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/*
 * payment methods, maybe calling the PaymentQr class to generate the QRCode
 */

@Repository
public class PaymentsImpl implements PaymentsDAO{
    

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public PaymentsImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public PaymentEntity save(PaymentEntity payment){
        em.persist(payment);
        return payment;
    }

    @Override
    public PaymentEntity findById(int id){
        try{
        TypedQuery<PaymentEntity> query = em
        .createQuery("SELECT p FROM Payments p WHERE p.id = :id", PaymentEntity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
        }catch(Exception e){
            System.err.println("Payment not found with id: " + id);
            return null;
        }
    }

    @Override
    public boolean deleteById(int id){

        PaymentEntity payment = findById(id);
        if (payment != null) {
            em.remove(payment);
            return true;
        }
        System.err.println("Payment not found with id: " + id);
        return false;
    }

    @Override
    public PaymentEntity updatePayment(PaymentEntity payment){
        PaymentEntity existingPayment = em.find(PaymentEntity.class, payment.getId());
        if (existingPayment != null) {
            em.merge(payment);
            return payment;
        }
        return null;
    }

    @Override
    public String findQrCodeById(int id){
        TypedQuery<String> query = em.createQuery("SELECT p.public_qrcode FROM Payments p WHERE p.id = :id", String.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("QR Code not found with id: " + id);
            return null;
        }

    }

    @Override
    public String findStatusById(int id){
        TypedQuery<String> query = em.createQuery("SELECT p.status FROM Payments p WHERE p.id = :id", String.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Status not found with id: " + id);
            return null;
        }
    }

    @Override
    public String findPaymentMethodById(int id){
        TypedQuery<String> query = em.createQuery("SELECT p.paymentMethod FROM Payments p WHERE p.id = :id", String.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Payment method not found with id: " + id);
            return null;
        }
    }

}

