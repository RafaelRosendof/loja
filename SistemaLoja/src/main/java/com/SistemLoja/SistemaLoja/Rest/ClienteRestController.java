package com.SistemLoja.SistemaLoja.Rest;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;
import com.SistemLoja.SistemaLoja.Service.ClienteService;
@RestController
@RequestMapping("/cliente")
public class ClienteRestController{

    private ClienteService clienteService;

    @Autowired
    public ClienteRestController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @DeleteMapping("/deletaCliente/{clienteId}")
    public void deleteCliente(@PathVariable int clienteId){
        ClienteEntity cliente = clienteService.findById(clienteId);

        if(cliente == null){
            throw new RuntimeException("Cliente não encontrado - " + clienteId);
        }
        clienteService.deleteById(clienteId);
    }

    @GetMapping("/public/ListClientes")
    public List<ClienteEntity> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/cliente/{clienteId}")
    public ClienteEntity getCliente(@PathVariable int clienteId){
        ClienteEntity cliente = clienteService.findById(clienteId);

        if(cliente == null){
            throw new RuntimeException("Cliente não encontrado - " + clienteId);
        }

        return cliente;

    }

    @PutMapping("{id}/updateCliente")
    public ClienteEntity updateCliente(@PathVariable int id ,  @RequestBody ClienteEntity cliente){
        cliente.setId(id);

        ClienteEntity updateCliente = clienteService.updateCliente(cliente);

        if(updateCliente == null){
            throw new RuntimeException("Cliente não encontrado - " + id);
        }
        return updateCliente;
    }

    @PostMapping("/adicionarCliente")
    public ClienteEntity addCliente(@RequestBody ClienteEntity cliente){
        clienteService.CriarCliente(cliente);
        //add the mail send here 
        clienteService.SendEmail(cliente.getId());

        return cliente;
    }

   @GetMapping("{id}/findSaldo")
   public ResponseEntity<Map<String , Object>> findSaldo(@PathVariable int id){
    BigDecimal saldo = clienteService.findSaldo(id);

    if(saldo == null){
        throw new RuntimeException("Cliente não encontrado - " + id);
    }
    Map<String , Object> response = new HashMap<>();
    response.put("Saldo" , saldo);
    return ResponseEntity.ok(response);
   } 
    

}