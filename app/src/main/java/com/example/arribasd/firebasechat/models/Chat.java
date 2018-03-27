package com.example.arribasd.firebasechat.models;

import java.util.ArrayList;

/**
 * Created by ArribasD on 3/26/2018.
 */

public class Chat {
    String id;
    String with;
    String lastMessage;
    ArrayList<Message> messages;

    public Chat(String id, ArrayList<Message> messages, String with, String lastMessage) {
        this.id = id;
        this.messages = messages;
        this.with = with;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
