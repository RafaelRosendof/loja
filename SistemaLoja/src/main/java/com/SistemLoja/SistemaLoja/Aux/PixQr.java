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

    public void createQrCode(){
        String filePath = "/PNG/qrCode.png";
        String chavePix = "84988278927";

        try{
            generateQrCode(chavePix, filePath);
            System.out.println("\n\n\n QR generate with success ");
        } catch( WriterException e){
            System.err.println("Could not generate the QRCode " + e);
        } catch( IOException e){
            System.err.println("Could not generate the QRCode " + e);
        }
    }

}

/*
 * 
00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-
4266554400005204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***63041D3D

https://www.bcb.gov.br/content/estabilidadefinanceira/pix/Regulamento_Pix/II_ManualdePadroesparaIniciacaodoPix.pdf pagina 18

 * 
 */