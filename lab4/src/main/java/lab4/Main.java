package lab4;

import java.io.IOException;
import java.util.List;

import lab4.configuration.Configurator;
import lab4.email.Email;
import lab4.prank.PrankGenerator;
import lab4.smtp.SMTPClient;

public class Main {
    public static void main(String[] args) {
        try{                   
            Configurator config = new Configurator("../src/main/java/lab4/config/messages.txt", 
                                                    "../src/main/java/lab4/config/victims.txt",
                                                    "../src/main/java/lab4/config/config.txt");
            int nb = config.getNb();
            SMTPClient smtp = new SMTPClient(config.getHost(), config.getPort());
            PrankGenerator ppg = new PrankGenerator(config, smtp, nb);

            List<Email> listo = ppg.generatePranks();
            ppg.sendEmails(listo);

        }catch(IOException e){
            System.out.println(e);
        }
    }
}