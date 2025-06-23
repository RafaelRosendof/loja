package com.SistemLoja.SistemaLoja.DAO;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.TypedQuery;

@Repository
public class ClienteImplDAO implements CustomDAOCliente{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ClienteImplDAO(EntityManager em){
        this.em = em;
    }

    @Override
    public ClienteEntity findByCpf(String cpf){
        TypedQuery<ClienteEntity> query = em.createQuery("SELECT f FROM ClienteEntity f WHERE f.cpf = :cpf", ClienteEntity.class);
        query.setParameter("cpf", cpf);
        return query.getSingleResult();
    }

    @Override
    public BigDecimal findSaldo(int id){
        TypedQuery<BigDecimal> query = em.createQuery("SELECT f.saldo FROM ClienteEntity f WHERE f.id = :id", BigDecimal.class);
        query.setParameter("id", id); 
        return query.getSingleResult();

    }

    @Override
    public String findEmail(int id){
        TypedQuery<String> query = em.createQuery("SELECT f.email FROM ClienteEntity f WHERE f.id = :id" , String.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public ClienteEntity updateCliente(ClienteEntity cliente){
        em.merge(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public ClienteEntity CriarCliente(ClienteEntity cliente){
        em.merge(cliente); //verificar pois o persist deu pau no produto
        return cliente;
    }

    @Override 
    public ClienteEntity findConta(int id){
        TypedQuery<ClienteEntity> query = em.createQuery("SELECT f FROM ClienteEntity f WHERE f.id = :id", ClienteEntity.class);
        query.setParameter("id", id); 
        return query.getSingleResult();
    }


}