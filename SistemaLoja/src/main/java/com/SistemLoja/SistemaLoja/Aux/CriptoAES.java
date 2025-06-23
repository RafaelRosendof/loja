package com.SistemLoja.SistemaLoja.Aux;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

@Component
public class CriptoAES {
    private final int GCM_IV_LENGTH = 12;
    private final String ALGORITHM = "AES/GCM/NoPadding";
    private final int GCM_TAG_LENGTH = 128; //or gonna be 256 

    @Value("${app.encryption.key}")
    private String secretKeyString; //chave do application
    private SecretKey key;

    //método chamado pelo Spring para injeção de dependencias 
    @PostConstruct
    public void init(){
        //decodifica a base64 e transforma em secretKey
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
        this.key = new SecretKeySpec(decodedKey, 0 , decodedKey.length , "AES");
    }

    public String Encrypt(String text) throws Exception{
        byte [] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key , gcmParameterSpec);

        byte[] cipherText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);

        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    public String Decrypt(String encrypted) throws Exception{
        byte[] decodedBytes = Base64.getDecoder().decode(encrypted);

        ByteBuffer byteBuffer = ByteBuffer.wrap(decodedBytes);

        byte[] iv = new byte[GCM_IV_LENGTH];
        byteBuffer.get(iv);

        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE , key , gcmParameterSpec);

        byte[] plaintext = cipher.doFinal(cipherText);

        return new String(plaintext , StandardCharsets.UTF_8);


    }
}
/*

    public static void main(String[] args) {
        try {
            // --- Step 1: Generate a secure AES key ---
            // In a real application, this key would be loaded securely, not generated on the fly.
            SecretKey secretKey = generateAesKey();
            System.out.println("AES Key Generated (Hex): " + bytesToHex(secretKey.getEncoded()));

            // --- Step 2: Define the data to encrypt ---
            String originalData = "This is some sensitive client data that needs to be secured.";
            System.out.println("\nOriginal Data: " + originalData);

            // --- Step 3: Encrypt the data ---
            String encryptedData = encrypt(originalData, secretKey);
            System.out.println("Encrypted Data (Base64): " + encryptedData);

            // --- Step 4: Decrypt the data ---
            String decryptedData = decrypt(encryptedData, secretKey);
            System.out.println("Decrypted Data: " + decryptedData);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to convert byte array to hex string (for key viewing, not for production logging of keys)
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

 */
