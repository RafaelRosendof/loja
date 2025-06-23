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
                //server.setAddress(InetAddress.getByName("2804:29b8:50ed:9394:ac8:3f89:46a6:f136"));
                server.setAddress(InetAddress.getByName("192.168.0.19"));
            } catch (UnknownHostException e) {
                throw new RuntimeException("Erro ao resolver o endereço IPv6", e);
            }
        };
    }
}
