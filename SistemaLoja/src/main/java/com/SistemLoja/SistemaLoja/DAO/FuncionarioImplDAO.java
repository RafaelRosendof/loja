package com.SistemLoja.SistemaLoja.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository

public class FuncionarioImplDAO implements CustomFuncDAO{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public FuncionarioImplDAO(EntityManager em){
        this.em = em;
    }

    @Override
    public FuncionarioEntity findByCpf(String cpf){
        TypedQuery<FuncionarioEntity> query = em.createQuery("SELECT f FROM FuncionarioEntity f WHERE f.cpf = :cpf", FuncionarioEntity.class);
        query.setParameter("cpf", cpf);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public FuncionarioEntity CriarFuncionario(FuncionarioEntity funcionario){
        em.merge(funcionario);
        return funcionario;

    }

    @Override
    @Transactional
    public FuncionarioEntity updateFuncionario(FuncionarioEntity funcionario){
        em.merge(funcionario);

        return funcionario;
    }

}