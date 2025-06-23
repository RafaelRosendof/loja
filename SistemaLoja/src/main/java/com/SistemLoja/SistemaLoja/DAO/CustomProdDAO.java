package com.SistemLoja.SistemaLoja.DAO;

import com.SistemLoja.SistemaLoja.Entity.ProdutoEntity;
import java.math.BigDecimal;
public interface CustomProdDAO {
 
    /*
     * aqui temos os métodos básicos para manipulação de dados no banco de dados
     * save saveAll findById findAll deleteById delete deleteAll 
     * 
     * eu devo fazer  CriarProduto updateProduto? findByNome 
     * UpdatePrice e UpdateQuantidade 
     */

    ProdutoEntity findByNome(String nome);
    ProdutoEntity CriarProduto(ProdutoEntity produto);
    ProdutoEntity updatePrice(BigDecimal price , int id);
    ProdutoEntity updateQuantidade(int value , int id);
    
}