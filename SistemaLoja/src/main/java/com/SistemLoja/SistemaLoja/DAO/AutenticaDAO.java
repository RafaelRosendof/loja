package com.SistemLoja.SistemaLoja.DAO;


import org.springframework.stereotype.Repository;

import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;
import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class AutenticaDAO{

    @PersistenceContext
    EntityManager entityManager;

    public AutenticaDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    

    @Transactional
    public ClienteEntity findCliente(String login , String senha){
        TypedQuery<ClienteEntity> query = entityManager.createQuery("SELECT c FROM ClienteEntity c WHERE c.login = :login AND c.senha = :senha", ClienteEntity.class);
        query.setParameter("login", login);
        query.setParameter("senha", senha);

        return query.getResultStream().findFirst().orElse(null);
    }

    @Transactional
    public FuncionarioEntity findFuncionario(String login , String senha, String cargo){
        TypedQuery<FuncionarioEntity> query = entityManager.createQuery("SELECT c FROM FuncionarioEntity c WHERE c.login = :login AND c.senha = :senha AND c.cargo = :cargo", FuncionarioEntity.class);
        query.setParameter("login", login);
        query.setParameter("senha", senha);
        query.setParameter("cargo", cargo);

        return query.getResultStream().findFirst().orElse(null);
    }

    @Transactional
    public FuncionarioEntity findGerente(String login, String senha , String cargo){
        TypedQuery<FuncionarioEntity> query = entityManager.createQuery("SELECT c FROM FuncionarioEntity c WHERE c.login = :login AND c.senha = :senha AND c.cargo = :cargo", FuncionarioEntity.class);
        query.setParameter("login", login);
        query.setParameter("senha", senha);
        query.setParameter("cargo", cargo);

        return query.getResultStream().findFirst().orElse(null);
    }

}




