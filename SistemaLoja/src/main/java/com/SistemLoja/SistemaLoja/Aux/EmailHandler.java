package com.SistemLoja.SistemaLoja.Aux;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class EmailHandler {

    @Bean 
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        
        mailSender.setUsername("rafinha.galego73@gmail.com");
        mailSender.setPassword("cdrl adgf ddin ejys");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }



    
}
//python src/main.py -c config/default_ch.yaml -g 5 && python src/main.py -c config/default_ch1.yaml -g 5 && python src/main.py -c config/default_ch2.yaml -g 5