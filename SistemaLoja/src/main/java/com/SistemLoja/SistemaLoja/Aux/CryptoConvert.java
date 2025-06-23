package com.SistemLoja.SistemaLoja.Aux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Component
@Converter(autoApply = false) // para aplicarmos manualmente onde quisermos 
public class CryptoConvert implements AttributeConverter<String , String> {

    private static CriptoAES criptoAES;

    @Autowired
    public void setCriptoAES(CriptoAES criptoAES){
        CryptoConvert.criptoAES = criptoAES;
    }

    @Override
    public String convertToDatabaseColumn(String attribute){

        if(attribute == null){
            return null;
        }

        try{
            return criptoAES.Encrypt(attribute);
        
        }catch(Exception e){
            throw new RuntimeException("Erro ao criptografar o artributo");
        }

    }

    @Override
    public String convertToEntityAttribute(String dbData){

        if(dbData == null){
            return null;
        }

        try{
            return criptoAES.Decrypt(dbData);
        
        }catch(Exception e){
            throw new RuntimeException("Erro ao descriptografar o dado " + e);
        }
        
    }
}
