package lab4.email;


import java.util.ArrayList;
import java.util.List;

public class Email {
    private String sender;
    private List<String> receivers;
    private String subject;
    private String body;

    public Email(){
        this.sender = "";
        this.receivers = new ArrayList<String>();
        this.subject = "";
        this.body = ""; 
    }

    public Email(String sender, List<String> receivers, String subject, String body){
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
