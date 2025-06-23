package com.SistemLoja.SistemaLoja.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;

public interface FuncionarioDAO extends JpaRepository<FuncionarioEntity, Integer> {
 

}