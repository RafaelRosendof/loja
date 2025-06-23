package com.SistemLoja.SistemaLoja.Entity;

import java.math.BigDecimal;

import com.SistemLoja.SistemaLoja.Aux.CryptoConvert;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "cliente")

public class ClienteEntity{

//@GeneratedValue(strategy = IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Convert(converter = CryptoConvert.class)
    @Column(name = "nome")
    private String nome;

    @Convert(converter = CryptoConvert.class)
    @Column(name = "cpf")
    private String cpf;

    @Convert(converter = CryptoConvert.class)
    @Column(name = "email")
    private String email;

    @Convert(converter = CryptoConvert.class)
    @Column(name = "telefone")
    private String telefone;

    @Convert(converter = CryptoConvert.class)
    @Column(name = "login")
    private String login;

    //@Convert(converter = CryptoConverter.class)
    @Convert(converter = CryptoConvert.class)
    @Column(name = "senha")
    private String senha;

    @Column(name = "saldo")
    private BigDecimal saldo;

    public ClienteEntity(){}

    public ClienteEntity(int id , String nome , String cpf , String email , String telefone ,String login , String senha , BigDecimal saldo){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.saldo = saldo;
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

    public String getCPF(){
        return cpf;
    }
    public void setCPF(String cpf){
        this.cpf = cpf;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getTelefone(){
        return telefone;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public BigDecimal getSaldo(){
        return saldo;
    }

    public void setSaldo(BigDecimal saldo){
        this.saldo = saldo;
    }






    //toString
    @Override
    public String toString(){
        return "USUÁRIO{ " +
        "id = " + id +
        ", Nome = "+ nome + '\''+
        ", cpf = " + cpf + '\'' +
        ", email = " + email + '\''+
        ", telefone = " + telefone + '\''+
        ", login = " + login + '\''+
        ", senha = " + senha + '\''+ 
        ", saldo = " + saldo + '\''+

        
        '}';
    }
}