package lab4.smtp;

import java.io.*;
import java.net.Socket;

import lab4.email.Email;

public class SMTPClient {

    private String serverAddress;
    private int serverPort;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SMTPClient(String serverAddress, int serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    private void writeAndFlush(String message) throws IOException{
        writer.write(message);
        writer.flush();
    }

    private void readAndCheck() throws IOException{
        String line = reader.readLine();
        
        if(line==null){
            return;
        }
        if(!line.startsWith("2")){
            throw new IOException("SMTP server returned error: " + line);
        }
    }
    
    public void sendEmail(Email email) throws IOException{
        //Create client socker and read server response
        socket = new Socket(serverAddress, serverPort);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readAndCheck();


        //Identify client to server and check server response
        writeAndFlush("EHLO "+serverAddress+"\r\n");
        reader.readLine();

        //Specify email sender and check server response
        writeAndFlush("MAIL FROM: <"+ email.getSender() +">\r\n");
        readAndCheck();

        //Specify email receivers and check server response
        for(String receiver : email.getReceivers()){
            writeAndFlush("RCPT TO: <"+ receiver +">\r\n");
            readAndCheck();
        }

        //Send email content to server
        writeAndFlush("DATA\r\n");
        writeAndFlush( "Content-Type: text/plain; charset=\"utf-8\" \"\r\n\"\r\n");
        writeAndFlush("From: "+email.getSender()+"\r\n");
        for(String receiver : email.getReceivers()){
            writeAndFlush("To: "+ receiver + "\r\n");
        }
        writeAndFlush("Subject: " + email.getSubject() + "\r\n");
        writeAndFlush(email.getBody());
        writeAndFlush("\r\n.\r\n");
        readAndCheck();

        //Quit and cleanup
        writeAndFlush("QUIT");
        writer.close();
        reader.close();
        socket.close();    

    }
}
