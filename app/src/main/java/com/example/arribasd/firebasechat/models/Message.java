package com.example.arribasd.firebasechat.models;

/**
 * Created by ArribasD on 3/26/2018.
 */

public class Message {
    String id, sender, timeStamp, data;

    public Message(String id, String sender, String timeStamp, String data) {
        this.id = id;
        this.sender = sender;
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
