package com.example.ft_hangout.Model;

public class Message {
    private String message;
    private String sender;
    private int id;

    public Message(String message, String sender, int id) {
        this.message = message;
        this.sender = sender;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
