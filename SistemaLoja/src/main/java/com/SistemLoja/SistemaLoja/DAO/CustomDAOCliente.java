package com.SistemLoja.SistemaLoja.DAO;

import java.math.BigDecimal;

import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;

public interface CustomDAOCliente  {
 
    
    /*
     * aqui temos os métodos básicos para manipulação de dados no banco de dados
     * save saveAll findById findAll deleteById delete deleteAll 
     * 
     * eu devo fazer findSaldo findConta MeusDados updateCliente? 
     * CriarCliente 
     */

    ClienteEntity findByCpf(String cpf);
    BigDecimal findSaldo(int id);
    ClienteEntity updateCliente(ClienteEntity cliente);
    ClienteEntity CriarCliente(ClienteEntity cliente);
    ClienteEntity findConta(int id);
    String findEmail(int id);

    
}