package com.SistemLoja.SistemaLoja.Config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> serverCustomize() {
        return server -> {
            server.setPort(8090);
            try {
                server.setAddress(InetAddress.getByName("172.18.0.1"));
                //server.setAddress(InetAddress.getByName("192.168.0.19"));
            } catch (UnknownHostException e) {
                throw new RuntimeException("Erro ao resolver o endereço IPv6", e);
            }
        };
    }
}
