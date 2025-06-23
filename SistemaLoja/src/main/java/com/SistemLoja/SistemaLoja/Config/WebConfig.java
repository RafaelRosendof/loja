/*
package com.SistemLoja.SistemaLoja.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    .addMapping("/**") // Aplica a regra a TODOS os endpoints da aplicação
                    .allowedOrigins(
                        "http://localhost:3000",
                        "http://192.168.0.19:3000"
                    ) // URL do seu frontend React em desenvolvimento
                    // .allowedOrigins("http://localhost:3000", "https://seu-dominio-de-producao.com")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                    .exposedHeaders("Authorization")
                    .allowedHeaders("*") // Permite todos os cabeçalhos na requisição
                    .allowCredentials(true); // Permite o envio de cookies e headers de autenticação
            }
        };
    };
}
*/
