package lab4.prank;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lab4.configuration.Configurator;
import lab4.email.Email;
//import lab4.smtp.SMTPClient;


public class PrankGenerator {
    private Configurator config;
    //private SMTPClient smtp;
    private int nb;
    private Random rand;

    public PrankGenerator(Configurator config, int nb){
        this.config = config;
        //this.smtp = smtp;
        this.nb = nb;
    }
    
    public List<Email> generatePranks(){
        List<String> messages = config.getMessages();
        List<String> victims = config.getVictims();
        List<Email> emails = new ArrayList<Email>();
        
        int size =0;

        for(int i=0;i<nb;i++){
            
            Email e = new Email();
            //Select random email message
            rand= new Random();
            String message = messages.get(rand.nextInt(messages.size()));
            e.setSubject(message.substring(0, message.indexOf("\n")));
            e.setBody(message.substring(message.indexOf("\n")+1));

            //Shuffle victims and select 2-5 of them
            Collections.shuffle(victims);
            rand = new Random();
            size=rand.nextInt(4)+2;
            List<String> r = new ArrayList<>(victims.subList(0, size));
            e.setSender(victims.get(0));
            r.remove(victims.get(0));
            e.setReceivers(r);
            emails.add(e);
        }
        return emails;
    }   

    /* 
    public void sendEmails(List<Email> emails) throws IOException{
        int i = 0;
        for (Email e : emails){
            i++;
            smtp.sendEmail(e);
            System.out.println("Sent email number " + i);
        }
    }
    */
}
