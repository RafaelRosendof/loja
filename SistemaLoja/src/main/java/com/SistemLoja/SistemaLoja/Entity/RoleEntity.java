package com.SistemLoja.SistemaLoja.Entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "role")
public class RoleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name = "role_name" , unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<FuncionarioEntity> funcionarios;

    public RoleEntity(){}

    public RoleEntity(String roleName){
        this.roleName = roleName;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getRoleName(){
        return roleName;
    }
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public Set<FuncionarioEntity> getFuncionarios(){
        return funcionarios;
    }

    public void setFuncionarios(Set<FuncionarioEntity> funcionarios){
        this.funcionarios = funcionarios;
    }


}
