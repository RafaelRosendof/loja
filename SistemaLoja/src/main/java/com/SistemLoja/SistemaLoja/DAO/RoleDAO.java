package com.SistemLoja.SistemaLoja.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemLoja.SistemaLoja.Entity.RoleEntity;

public interface RoleDAO extends JpaRepository<RoleEntity , Integer>{

    RoleEntity findByRoleName(String roleName);
    
}
