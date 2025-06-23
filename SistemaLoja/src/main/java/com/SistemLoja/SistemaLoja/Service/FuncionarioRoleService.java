package com.SistemLoja.SistemaLoja.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemLoja.SistemaLoja.DAO.CustomFuncDAO;
import com.SistemLoja.SistemaLoja.DAO.FuncionarioDAO;
import com.SistemLoja.SistemaLoja.DAO.RoleDAO;
import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;
import com.SistemLoja.SistemaLoja.Entity.RoleEntity;

@Service
public class FuncionarioRoleService {
    
    @Autowired
    private CustomFuncDAO funcionarioDAO;
    private FuncionarioDAO funcionarioCusDAO;

    @Autowired
    private RoleDAO roleDAO;

    public void addRoleToFuncionario(String cpf , String roleName){
        FuncionarioEntity funcionario = funcionarioDAO.findByCpf(cpf);
        RoleEntity role = roleDAO.findByRoleName(roleName);
        funcionario.addRole(role);
        funcionarioCusDAO.save(funcionario);
    }

    public void removeRoleFromFuncionario(String cpf , String roleName){
        FuncionarioEntity funcionario = funcionarioDAO.findByCpf(cpf);
        RoleEntity role = roleDAO.findByRoleName(roleName);
        funcionario.removeRole(role);
        funcionarioCusDAO.save(funcionario);
    }

}
