package com.SistemLoja.SistemaLoja.Service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.SistemLoja.SistemaLoja.DAO.ProdutoDAO;
import com.SistemLoja.SistemaLoja.Entity.ProdutoEntity;
import com.SistemLoja.SistemaLoja.DAO.CustomProdDAO;

/*
 * 
 *     ProdutoEntity findByNome(String nome);
    void CriarProduto(ProdutoEntity produto);
    void updatePrice(BigDecimal price , int id);
    void updateQuantidade(int value , int id);
    post put delete get
 */

@Service
public class ProdutoService{
    private final ProdutoDAO produtoDAO;
    private final CustomProdDAO customDAOProduto;

    public ProdutoService(ProdutoDAO produtoDAO , CustomProdDAO customDAOProduto){
        this.produtoDAO = produtoDAO;
        this.customDAOProduto = customDAOProduto;
    }

    public ProdutoEntity findByNome(String nome){
        return customDAOProduto.findByNome(nome);
    }

    public ProdutoEntity CriarProduto(ProdutoEntity produto){
        return customDAOProduto.CriarProduto(produto);
    }

    public ProdutoEntity updatePrice(BigDecimal price , int id){
        return customDAOProduto.updatePrice(price , id);
    }
    
    public ProdutoEntity updateQuantidade(int value , int id){
        return customDAOProduto.updateQuantidade(value , id);
    }

    public ProdutoEntity findById(int id){
        return produtoDAO.findById(id).orElse(null);
    }

    public void deleteById(int id){
        produtoDAO.deleteById(id);
    }

    public List<ProdutoEntity> findAll(){
        return produtoDAO.findAll();
    }

}
