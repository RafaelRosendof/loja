package com.SistemLoja.SistemaLoja.Aux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;  


@Service
public  class MailSender implements EmailService  {

    @Autowired
    private JavaMailSender mailSender; 

    public boolean sendEmail(String to){

        String template = """
                Olá, o senhor está recebendo este email, devido ao cadastro na nossa empresa de credito.
                O seu email é: %s
                O seu assunto é: %s

                Caso tenha alguma dúvida, entre em contato com o suporte.
                Atenciosamente,
                Equipe de Suporte
                ps: não tente me encontrar 
                """;

        String subject = "cadastro do cliente";
        String body = "Cadastro do cliente para o figas";

        try {

            SimpleMailMessage  message = new SimpleMailMessage();
            message.setFrom("rafinha.galego73@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(String.format(template, to, body));

            mailSender.send(message);
            System.out.println("Email enviado com sucesso para: " + to);
            return true;
            
        } catch (Exception e) {
            
            System.err.println("Erro ao enviar email: " + e.getMessage());
            e.printStackTrace();
            return false;
        
        }
    }


    /*
     * send Email 
     * Recive email
     * put the reference in the service package 
     */
}
