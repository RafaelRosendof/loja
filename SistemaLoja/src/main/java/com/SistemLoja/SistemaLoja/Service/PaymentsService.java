package com.SistemLoja.SistemaLoja.Service;

import com.SistemLoja.SistemaLoja.DAO.ProdutoDAO;
import com.SistemLoja.SistemaLoja.Aux.PaymentQr;
import com.SistemLoja.SistemaLoja.Entity.PaymentEntity;
import com.SistemLoja.SistemaLoja.DAO.PaymentsDAO;

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

}
