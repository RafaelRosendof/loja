package com.SistemLoja.SistemaLoja.Entity;

/*
 * categoria
 * preço
 * nome
 * quantidade
 * id
 * marca
 */


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produto")

public class ProdutoEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;


    @Column(name = "nome")
    private String nome;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "preço")
    private BigDecimal price;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "marca")
    private String marca;

    public ProdutoEntity(){}

    public ProdutoEntity(int id , String nome , String categoria , BigDecimal price , int quantidade , String marca){
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.price = price;
        this.quantidade = quantidade;
        this.marca = marca;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCategoria(){
        return categoria;
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public String getMarca(){
        return marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }


    @Override
    public String toString(){
        return "ProdutoEntity [id=" + id + ", nome=" + nome + ", categoria=" + categoria + ", price=" + price + ", quantidade=" + quantidade + ", marca=" + marca + "]";
    }

}