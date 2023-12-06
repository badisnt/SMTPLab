package lab4.configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class Configurator {
    private List<String> messages;
    private List<String> victims;
    private int nb;
    private String host;
    private int port;

    public Configurator(String messagesFile, String victimsFile, String configsFile) throws IOException{
        victims = readVictims(victimsFile);
        messages = readMessages(messagesFile);
        getConfigs(configsFile);
    }

    public List<String> getMessages() {
        return messages;
    }


    public List<String> getVictims() {
        return victims;
    }

    public String getHost() {
        return host;
    }


    public int getPort() {
        return port;
    }

    public int getNb(){
        return nb;
    }

    public List<String> readMessages(String messagesFile) throws IOException{
        List<String> messageList;
        StringBuilder  builder;
        String line;
        Boolean expectingSubject;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(messagesFile), "UTF-8"))){
            messageList = new ArrayList<String>();
            builder = new StringBuilder();
            expectingSubject = true;
            line=reader.readLine();
            while (line != null){
                if(expectingSubject){
                    if(line.startsWith("Subject:")){
                        builder.append(line.substring(9)).append("\r\n");
                        expectingSubject = false;
                    }else if(line.isEmpty()){
                        line = reader.readLine();
                        continue;
                    }else{
                        throw new IOException("Message doesn't have subject: " + line);
                    }
                }else{
                    if(line.equals("***")){
                        messageList.add(builder.toString());
                        builder.setLength(0);
                        expectingSubject = true;
                    }else{
                        builder.append(line).append("\r\n");
                    }
                }
                line = reader.readLine();
                if (line==null){
                    messageList.add(builder.toString());
                    builder.setLength(0);
                }

            }
            reader.close();
        }
        return messageList;
    }

    //need to add check if at least 2nb
    public List<String> readVictims(String victimsFile) throws IOException{
        List<String> victimList;
        String line;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(victimsFile), "UTF-8"))){
            victimList = new ArrayList<String>();
            while ((line= reader.readLine()) != null){
                if (Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", line)){
                    victimList.add(line);
                }else{
                    throw new IOException("List of victims contains an invalid email address: "+ line);
                }
            }
            reader.close();
        }
        
        return victimList;
    }

    public void getConfigs(String configsFile) throws IOException{
        FileInputStream s = new FileInputStream(configsFile);
        Properties properties = new Properties();
        properties.load(s);
        this.host = properties.getProperty("serverAddress");
        this.port = Integer.parseInt(properties.getProperty("serverPort"));
        this.nb = Integer.parseInt(properties.getProperty("nb"));
    }

}
