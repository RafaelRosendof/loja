package com.SistemLoja.SistemaLoja.DAO;

import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;

public interface CustomFuncDAO {
 
    /*
     * aqui temos os métodos básicos para manipulação de dados no banco de dados
     * save saveAll findById findAll deleteById delete deleteAll 
     * 
     * eu devo fazer  CriarFuncionario updateFuncionario? findByCPF 
     */

    FuncionarioEntity findByCpf(String cpf); 

    FuncionarioEntity CriarFuncionario(FuncionarioEntity funcionario);

    FuncionarioEntity updateFuncionario(FuncionarioEntity funcionario);
}