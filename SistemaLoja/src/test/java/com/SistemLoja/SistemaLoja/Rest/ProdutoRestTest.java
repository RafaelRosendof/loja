package com.SistemLoja.SistemaLoja.Rest;

import com.SistemLoja.SistemaLoja.Entity.ProdutoEntity;
import com.SistemLoja.SistemaLoja.Service.ProdutoService;

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

import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoRestTest{

    @Autowired 
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Test 
    public void testGetLista() throws Exception{
        ProdutoEntity p1 = new ProdutoEntity(0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(10.35) , 10 , "Gillette");
        ProdutoEntity p2 = new ProdutoEntity(1 , "Creme" , "Cremes" , BigDecimal.valueOf(5.35) , 20 , "Nivea");
        ProdutoEntity p3 = new ProdutoEntity(2 , "Shampoo" , "Shampoos" , BigDecimal.valueOf(15.35) , 30 , "Pantene");

        List<ProdutoEntity> produtos = Arrays.asList(p1 , p2 , p3);

        when(produtoService.findAll()).thenReturn(produtos);

        mockMvc.perform(get("/produto/ListarProdutos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(produtos.size()));
    }

    @Test
    public void testDeletaP() throws Exception{
        ProdutoEntity p1 = new ProdutoEntity(0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(10.35) , 10 , "Gillette");

        when(produtoService.findById(0)).thenReturn(p1);
        doNothing().when(produtoService).deleteById(p1.getId());

        mockMvc.perform(delete("/produto/delete/{id}",p1.getId()))
            .andExpect(status().isOk());

        verify(produtoService , times(1)).deleteById(p1.getId());

    }

    @Test
    public void getProduto() throws Exception{
        ProdutoEntity p2 = new ProdutoEntity(1 , "Creme" , "Cremes" , BigDecimal.valueOf(5.35) , 20 , "Nivea");

    when(produtoService.findById(p2.getId())).thenReturn(p2);

    mockMvc.perform(get("/produto/produtos/{id}", p2.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(p2.getId()))
        .andExpect(jsonPath("$.nome").value(p2.getNome()))
        .andExpect(jsonPath("$.categoria").value(p2.getCategoria()))
        .andExpect(jsonPath("$.price").value(p2.getPrice().doubleValue()))
        .andExpect(jsonPath("$.quantidade").value(p2.getQuantidade()))
        .andExpect(jsonPath("$.marca").value(p2.getMarca()));

    verify(produtoService, times(1)).findById(p2.getId());
    }

    @Test
    public void testAddProduto() throws Exception{
        ProdutoEntity p1 = new ProdutoEntity (0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(10.35) , 10 , "Gillette");
  //      ProdutoEntity p2 = new ProdutoEntity(1 , "Creme" , "Cremes" , BigDecimal.valueOf(5.35) , 20 , "Nivea");
//        ProdutoEntity p3 = new ProdutoEntity(2 , "Shampoo" , "Shampoos" , BigDecimal.valueOf(15.35) , 30 , "Pantene");

        when(produtoService.CriarProduto(any(ProdutoEntity.class))).thenReturn(p1);
       // when(produtoService.CriarProduto(any(ProdutoEntity.class))).thenReturn(p2);
       // when(produtoService.CriarProduto(any(ProdutoEntity.class))).thenReturn(p3);


        mockMvc.perform(post("/produto/adicionarProduto")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\"Gilete\",\"categoria\":\"Barbeadores\",\"price\":10.35,\"quantidade\":10,\"marca\":\"Gillette\"}"))
            .andExpect(jsonPath("$.id").value(p1.getId()))
            .andExpect(jsonPath("$.nome").value(p1.getNome()))
            .andExpect(jsonPath("$.categoria").value(p1.getCategoria()))
            .andExpect(jsonPath("$.price").value(p1.getPrice().doubleValue()))
            .andExpect(jsonPath("$.quantidade").value(p1.getQuantidade()))
            .andExpect(jsonPath("$.marca").value(p1.getMarca())); 

        verify(produtoService , times(1)).CriarProduto(any(ProdutoEntity.class));


    }

    @Test
    public void testUpdatePrice() throws Exception{
        ProdutoEntity p1 = new ProdutoEntity(0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(10.35) , 10 , "Gillette");
        ProdutoEntity p2 = new ProdutoEntity(0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(5.35) , 10 , "Gillette");


        when(produtoService.findById(p1.getId())).thenReturn(p1);
        when(produtoService.updatePrice(p2.getPrice() , p1.getId())).thenReturn(p2);

        mockMvc.perform(put("/produto/{id}/updatePrice", p1.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content("5.35"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(p1.getId()))
            .andExpect(jsonPath("$.nome").value(p1.getNome()))
            .andExpect(jsonPath("$.price").value(5.35))
            .andExpect(jsonPath("$.quantidade").value(p1.getQuantidade()))
            .andExpect(jsonPath("$.marca").value(p1.getMarca()));
    }

    @Test
    public void testUpdateQuantidade() throws Exception{
        ProdutoEntity p1 = new ProdutoEntity(0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(10.35) , 10 , "Gillette");
        ProdutoEntity p2 = new ProdutoEntity(0 , "Gilete" , "Barbeadores" , BigDecimal.valueOf(10.35) , 30 , "Gillette");

        when(produtoService.findById(p1.getId())).thenReturn(p1);
        when(produtoService.updateQuantidade(p2.getQuantidade() , p1.getId())).thenReturn(p2);

        mockMvc.perform(put("/produto/{id}/updateQuantidade", p1.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content("30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(p1.getId()))
            .andExpect(jsonPath("$.nome").value(p1.getNome()))
            .andExpect(jsonPath("$.price").value(p1.getPrice().doubleValue()))
            .andExpect(jsonPath("$.quantidade").value(30))
            .andExpect(jsonPath("$.marca").value(p1.getMarca()));
    }
}

/*
 * 
 * @Test
public void updatePriceTest() throws Exception {
    // Dados do produto existente
    ProdutoEntity produtoExistente = new ProdutoEntity(1, "Creme", "Cremes", BigDecimal.valueOf(5.35), 20, "Nivea");
    ProdutoEntity produtoAtualizado = new ProdutoEntity(1, "Creme", "Cremes", BigDecimal.valueOf(10.00), 20, "Nivea");

    // Mock do serviço para encontrar o produto e atualizar o preço
    when(produtoService.updatePrice(BigDecimal.valueOf(10.00), produtoExistente.getId())).thenReturn(produtoAtualizado);

    // Requisição para atualizar o preço
    mockMvc.perform(put("/produto/{id}/updatePrice", produtoExistente.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content("10.00")) // Corpo com o novo preço
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(produtoExistente.getId()))
        .andExpect(jsonPath("$.nome").value(produtoExistente.getNome()))
        .andExpect(jsonPath("$.preco").value(10.00))
        .andExpect(jsonPath("$.quantidade").value(produtoExistente.getQuantidade()))
        .andExpect(jsonPath("$.marca").value(produtoExistente.getMarca()));

    // Verifica se o método do serviço foi chamado
    verify(produtoService, times(1)).updatePrice(BigDecimal.valueOf(10.00), produtoExistente.getId());
}

 */