package lab4.prank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lab4.configuration.Configurator;
import lab4.email.Email;
import lab4.smtp.SMTPClient;


public class PrankGenerator {
    private Configurator config;
    private SMTPClient smtp;
    private int nb;

    public PrankGenerator(Configurator config, SMTPClient smtp, int nb){
        this.config = config;
        this.smtp = smtp;
        this.nb = nb;
    }
    
    public List<Email> generatePranks(){
        List<String> messages = config.getMessages();
        List<String> victims = config.getVictims();
        List<Email> emails = new ArrayList<Email>();
        Random rand;
        int size =0;

        for(int i=0;i<nb;i++){
            rand= new Random();
            Email e = new Email();
            String message = messages.get(rand.nextInt(messages.size()));
            
            e.setSubject(message.substring(0, message.indexOf("\n")));
            e.setBody(message.substring(message.indexOf("\n")+1));
            int nbV = victims.size();
            e.setSender(victims.get(i));
            List<String> r = new ArrayList<String>();
            rand = new Random();
            size=rand.nextInt(4)+1;
            for(int j=0; j<size;j++){
                r.add(victims.get(rand.nextInt(victims.size())));
            }
            e.setReceivers(r);
            emails.add(e);
        }
        return emails;
    }   

    public void sendEmails(List<Email> emails) throws IOException{
        for (Email e : emails){
            smtp.sendEmail(e);
        }
    }
}
