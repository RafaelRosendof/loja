package com.SistemLoja.SistemaLoja.Aux;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.ErrorCorrection;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class PixQr {
    



    public void generateQrCode(String chavePix , String filePath) throws WriterException , IOException{

        //code here for generate qrCode

        QRCodeWriter qrCode = new QRCodeWriter();

        Map<EncodeHintType , Object> hints = new HashMap<>();

        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // L = Low, M, Q, H = High
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix biMatrix = qrCode.encode(chavePix ,  BarcodeFormat.QR_CODE , 400 , 400);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(biMatrix, "PNG", path);

    }

    public String generateBtcQrCode(){

        String filePath = "/PNG/"+ java.util.UUID.randomUUID().toString()+"btcQrCode_" + System.currentTimeMillis() + ".png";
        String publicKey = "1A1zP1e5QGefi2DMPTfTL5SLmv7DivfNa";
        try{
            generateQrCode(publicKey, filePath);
        } catch (WriterException | IOException e) {
            System.err.println("Could not generate the BTC QRCode " + e);
        }

        String base64Qr = CreateQr64base(filePath);
        if(base64Qr == null){
            System.err.println("Could not convert the QRCode to base64");
            return null;
    }
        return base64Qr;
    }


    public String createEthQrCode(){
        //String filePath = "/PNG/ethQrCode.png";
        //file path must be a random name to avoid concurrent clients generation as the same file
        String filePath = "/PNG/"+ java.util.UUID.randomUUID().toString()+"ethQrCode_" + System.currentTimeMillis() + ".png";
        String publicKey = "0x0985be57E3FB6530FCBeAEaEA280F07a53B90B63";
        try{
            generateQrCode(publicKey, filePath);
            System.out.println("\n\n\n ETH QR generate with success ");
        } catch( WriterException e){
            System.err.println("Could not generate the ETH QRCode " + e);
        } catch( IOException e){
            System.err.println("Could not generate the ETH QRCode " + e);
        }

        String base64Qr = CreateQr64base(filePath);
        if(base64Qr == null){
            System.err.println("Could not convert the QRCode to base64");
            return null;
    }
        return base64Qr;

    }

    
    public String createPixQrCode(float valor){
        String filePath = "/PNG/qrPixCode.png";
        String chavePix = "e647e494-5c12-401a-ae3a-ed498cab4406";
        String merchantCity = "NATAL";
        String merchantName = "Rafael Rosendo Faustino";
        String CountryCode = "BR";
        String GUI = "br.gov.bcb.pix";

        String pixString = String.format(
            "00020101021126580014%s0136%s5204000053039865802%s5913%s6008%s62070503***63041D3D",
            CountryCode, chavePix, GUI, merchantName, merchantCity
        );


        try{
            generateQrCode(pixString, filePath);
            System.out.println("\n\n\n QR generate with success ");
        } catch( WriterException e){
            System.err.println("Could not generate the QRCode " + e);
        } catch( IOException e){
            System.err.println("Could not generate the QRCode " + e);
        }
        String base64Qr = CreateQr64base(filePath);
        if(base64Qr == null){
            System.err.println("Could not convert the QRCode to base64");
            return null;
    }
        return base64Qr;
    }


    public String CreateQr64base(String filePath) {

        try{
            Path path = FileSystems.getDefault().getPath(filePath);
            byte[] fileBytes = java.nio.file.Files.readAllBytes(path);
            String base64String = java.util.Base64.getEncoder().encodeToString(fileBytes);

            // Delete the file after reading 
            java.nio.file.Files.deleteIfExists(path);

            return base64String;
        } catch(IOException e){
            System.err.println("Could not read the file " + e);
            return null;
        }
    }

}

/*
 * 
00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-
4266554400005204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D

https://www.bcb.gov.br/content/estabilidadefinanceira/pix/Regulamento_Pix/II_ManualdePadroesparaIniciacaodoPix.pdf pagina 26

 * 
 * 
 * 


 Ajustar os qrCodes para base 64 

 Fazer a string do pix também como acima 
 */