package com.example.ktw.mykakao;

/**
 * Created by ktw on 2017-07-23.
 */

public class Message {

    private String id;
    private String sender;
    private String text;

    public Message (){

    }

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
