package com.SistemLoja.SistemaLoja.Service;

import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemLoja.SistemaLoja.DAO.AutenticaDAO;
import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;


@Service
public class AutenticaService{

    @Autowired
    private final AutenticaDAO autenticaDAO;

    public AutenticaService(AutenticaDAO autenticaDAO){
        this.autenticaDAO = autenticaDAO;
    }

    public ClienteEntity findCliente(String login , String senha){
        return autenticaDAO.findCliente(login , senha);
    }

    public FuncionarioEntity findFuncionario(String login , String senha , String cargo){
        return autenticaDAO.findFuncionario(login , senha , cargo);
    }
}