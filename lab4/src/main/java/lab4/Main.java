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
            Configurator config = new Configurator("../../config/messages.txt", 
                                                    "../../config/victims.txt",
                                                    "../../config/config.txt");
            int nb = config.getNb();
            SMTPClient smtp = new SMTPClient(config.getHost(), config.getPort());
            PrankGenerator ppg = new PrankGenerator(config, nb);

            List<Email> list = ppg.generatePranks();
            int i= 0;
            for (Email e : list){
                i++;
                smtp.sendEmail(e);
                System.out.println("Sent email number " + i);
            }

        }catch(IOException e){
            System.out.println(e);
        }
    }
}