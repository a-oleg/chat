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

}
