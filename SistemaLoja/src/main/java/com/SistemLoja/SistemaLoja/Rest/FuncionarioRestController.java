package com.SistemLoja.SistemaLoja.Rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SistemLoja.SistemaLoja.Service.FuncionarioService;
import com.SistemLoja.SistemaLoja.Entity.FuncionarioEntity;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @Por enquanto ficamos somente assim
 * Mas falta implementar quem sabe mais métodos 
 *  
 */

@RestController
@RequestMapping("/funcionario")
public class FuncionarioRestController {
    
    private FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRestController(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

     @GetMapping("/Listfuncionarios")
     public List<FuncionarioEntity> findAll(){
         return funcionarioService.findAll();
     }

     @GetMapping("/funcionarios/{funcionarioId}")
     public FuncionarioEntity getFuncionario(@PathVariable int funcionarioId){
         FuncionarioEntity funcionario = funcionarioService.findById(funcionarioId);

         if(funcionario == null){
             throw new RuntimeException("Funcionario não encontrado - " + funcionarioId);
         }

         return funcionario;

     }

    @PostMapping("/adicionarFuncionario")
    public FuncionarioEntity addFunc(@RequestBody FuncionarioEntity funcionario){
        funcionarioService.CriarFuncionario(funcionario);
        return funcionario;
    }

    @PostMapping("/atualizarFuncionario")
    public FuncionarioEntity updateFunc(@RequestBody FuncionarioEntity funcionario){
        funcionarioService.updateFuncionario(funcionario);
        return funcionario;
    }
    
    @DeleteMapping("/deletarFuncionario/{funcionarioId}")
    public void deleteFunc(@PathVariable int funcionarioId){

        FuncionarioEntity funcionario = funcionarioService.findById(funcionarioId);

        if(funcionario == null){
            throw new RuntimeException("Funcionario não encontrado - " + funcionarioId);
        }

        funcionarioService.deleteById(funcionarioId);
    }

    @GetMapping("/funcionarios/{cpf}")
    public FuncionarioEntity getByCPF(@PathVariable String cpf){
        FuncionarioEntity func = funcionarioService.findByCpf(cpf);

        if(func == null){
            throw new RuntimeException("Funcionario não encontrado - " + cpf);
        }

        return func;
    }
}