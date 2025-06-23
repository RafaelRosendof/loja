package com.SistemLoja.SistemaLoja.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SistemLoja.SistemaLoja.Entity.ProdutoEntity;
public interface ProdutoDAO extends JpaRepository<ProdutoEntity, Integer> {
 

}