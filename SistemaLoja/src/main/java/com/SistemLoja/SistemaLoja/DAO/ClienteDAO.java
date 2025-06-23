package com.SistemLoja.SistemaLoja.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;

public interface ClienteDAO extends JpaRepository<ClienteEntity, Integer> {
 
    
    /*
     * aqui temos os métodos básicos para manipulação de dados no banco de dados
     * save saveAll findById findAll deleteById delete deleteAll 
     * 
     * eu devo fazer findSaldo findConta MeusDados updateCliente? 
     * CriarCliente 
     */

}