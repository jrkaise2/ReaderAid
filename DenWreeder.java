package com.mycompany.wreeder;

import com.ibm.watson.developer_cloud.text_to_speech.v1.*;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import sun.audio.*;
import java.io.*;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DenWreeder {

    TextToSpeech service = null;
    private String user = "51e7a55e-ee0e-437f-b6a7-14c8626a6ec5";
    private String pass = "flHi3JruVnK4";
    //jrkaise2
    //WqOTkKZvfpTaR6Q_fr1gYiaNH_nXr42wMGly6DGumfkL

    public DenWreeder() {
        this.service = new TextToSpeech();
    }

    protected void authenticate() {
        this.service = new TextToSpeech();
        this.service.setUsernameAndPassword(this.user, this.pass);
    }
    
    protected String parsePDF(String file) throws FileNotFoundException, IOException{
        PDDocument pdoc = null;
        COSDocument cdoc = null;
        PDFTextStripper stripper = null;
        String txt = "";
//        String file = "C:/Users/yo/Documents/NetBeansProjects/Wreeder/source.pdf";
        try{
            pdoc = PDDocument.load(new File(file));
            stripper = new PDFTextStripper();
            String str = stripper.getText(pdoc);
//            System.out.println(str);
            return str;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    protected AudioStream speak(String str, String voice) {
        try {
            String text = str;
            InputStream stream = this.service.synthesize(text, Voice.getByName(voice),
                    AudioFormat.WAV).execute();
            InputStream in = WaveUtils.reWriteWaveHeader(stream);
            File file = new File("C:/Users/yo/Documents/NetBeansProjects/Wreeder/speech.wav");
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
            stream.close();
            
            InputStream input = new FileInputStream(file);
            AudioStream audioStream = new AudioStream(input);
            AudioPlayer.player.start(audioStream);
            return audioStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {

    }
}
