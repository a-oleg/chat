package oleg.models;

public class Message {
    public Message(String sender, String reciver, String text, String status) {
        this.sender = sender;
        this.reciver = reciver;
        this.text = text;
        this.status = status;
    }

    private String sender;
    private String reciver;
    private String text;
    private String status;

    private String ipReciver;
    private int portReciver;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpReciver() {return ipReciver;}

    public void setIpReciver(String ipReciver) {this.ipReciver = ipReciver;}

    public int getPortReciver() {return portReciver;}

    public void setPortReciver(int portReciver) {this.portReciver = portReciver;}
}
