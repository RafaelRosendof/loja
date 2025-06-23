package com.SistemLoja.SistemaLoja.Rest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * 
 * 
 * Comando mvn test -Dtest=ClienteRestControllerTest

 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.SistemLoja.SistemaLoja.Entity.ClienteEntity;
import com.SistemLoja.SistemaLoja.Service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteRestControllerTest{


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAll() throws Exception{
        ClienteEntity cliente1 = new ClienteEntity(1 , "Rafael" , "7021666450", "rafinha.galego73@gmail.com", "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250));
        ClienteEntity cliente2 = new ClienteEntity(2 , "Firuza" , "702166", "figas@gmail.com", "84988278927" , "figas", "figas123", BigDecimal.valueOf(1550));
        ClienteEntity cliente3 = new ClienteEntity(3 , "Figao" , "702166", "firuza@gmail.com", "84988278927" , "firuza", "firuza123", BigDecimal.valueOf(1750));

        List<ClienteEntity> clientes = Arrays.asList(cliente1, cliente2, cliente3);

        when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/cliente/ListClientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(clientes.size()));
    }

    @Test
    public void testDeleteCliente() throws Exception{
        ClienteEntity cliente1 = new ClienteEntity(1 , "Rafael" , "7021666450", "rafinha.galego73@gmail.com", "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250));

        when(clienteService.findById(1)).thenReturn(cliente1);
        doNothing().when(clienteService).deleteById(1);

        mockMvc.perform(delete("/cliente/deletaCliente/1"))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).deleteById(1);

    }

    @Test
    public void testGetCliente() throws Exception{
        ClienteEntity cliente1 = new ClienteEntity(1 , "Rafael" , "7021666450", "rafinha.galego73@gmail.com", "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250));
        
        when(clienteService.findById(cliente1.getId())).thenReturn(cliente1);

        //doNothing().when(clienteService)

        mockMvc.perform(get("/cliente/cliente/{id}", cliente1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente1.getId()))
                .andExpect(jsonPath("$.nome").value(cliente1.getNome()))
                .andExpect(jsonPath("$.cpf").value(cliente1.getCPF()))
                .andExpect(jsonPath("$.email").value(cliente1.getEmail()))
                .andExpect(jsonPath("$.telefone").value(cliente1.getTelefone()))
                .andExpect(jsonPath("$.login").value(cliente1.getLogin()))
                .andExpect(jsonPath("$.senha").value(cliente1.getSenha()))
                .andExpect(jsonPath("$.saldo").value(cliente1.getSaldo()));

        verify(clienteService, times(1)).findById(1);
        

    }

    @Test
    public void testUpdateCliente() throws Exception{

        ClienteEntity cliente1 = new ClienteEntity(3 , "Rafael" , "7021666450", "rafinha.galego73@gmail.com", "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250));
        
        ClienteEntity novoCliente1 = new ClienteEntity(3 , "Figas" , "7021666450", "rafinha.galego73@gmail.com", "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250));

        when(clienteService.findById(cliente1.getId())).thenReturn(cliente1);
        when(clienteService.updateCliente(any(ClienteEntity.class))).thenReturn(novoCliente1);


        mockMvc.perform(put("/cliente/{id}/updateCliente", cliente1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCliente1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente1.getId()))
                .andExpect(jsonPath("$.nome").value("Figas"))
                .andExpect(jsonPath("$.cpf").value(cliente1.getCPF()))
                .andExpect(jsonPath("$.email").value(cliente1.getEmail()))
                .andExpect(jsonPath("$.telefone").value(cliente1.getTelefone()))
                .andExpect(jsonPath("$.login").value(cliente1.getLogin()))
                .andExpect(jsonPath("$.senha").value(cliente1.getSenha()))
                .andExpect(jsonPath("$.saldo").value(cliente1.getSaldo()));

    }


    @Test
    public void testAddCliente() throws Exception{

        ClienteEntity cliente1 = new ClienteEntity(0 , "Rafael" , "7021666450", "rafinha.galego73@gmail.com", "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250.0));

            //when(clienteService.CriarCliente(any(ClienteEntity.class)).thenReturn(cliente1);
            when(clienteService.CriarCliente(any(ClienteEntity.class))).thenReturn(cliente1);

            mockMvc.perform(post("/cliente/adicionarCliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Rafael\", \"cpf\": \"7021666450\", \"email\": \"rafinha.galego73@gmail.com\", \"telefone\": \"84988278927\", \"login\": \"rafael\", \"senha\": \"rafael123\", \"saldo\": 1250.00}"))
                .andExpect(jsonPath("$.id").value(cliente1.getId()))
                .andExpect(jsonPath("$.nome").value(cliente1.getNome()))
                .andExpect(jsonPath("$.cpf").value(cliente1.getCPF()))
                .andExpect(jsonPath("$.email").value(cliente1.getEmail()))
                .andExpect(jsonPath("$.telefone").value(cliente1.getTelefone()))
                .andExpect(jsonPath("$.login").value(cliente1.getLogin()))
                .andExpect(jsonPath("$.senha").value(cliente1.getSenha()))
                .andExpect(jsonPath("$.saldo").value(cliente1.getSaldo()));

            verify(clienteService , times(1)).CriarCliente(any(ClienteEntity.class));
    }

    @Test
    public void testFindSaldo() throws Exception{
        ClienteEntity c1 = new ClienteEntity(0 , "Rafael" , "7021666450", "rafinha.galego73@gmail.com" , "84988278927" , "rafael", "rafael123", BigDecimal.valueOf(1250.0));

        when(clienteService.findSaldo(c1.getId())).thenReturn(c1.getSaldo());


    mockMvc.perform(get("/cliente/{id}/findSaldo", c1.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Saldo").value(c1.getSaldo()));
    }
} 