package com.example.arribasd.firebasechat.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ArribasD on 3/26/2018.
 */

public class Message {
    String id, from, to, message, timestamp;

    public Message(String id, String from, String to, String message, String timestamp) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("from", from);
        result.put("to", to);
        result.put("message", message);
        result.put("timestamp", timestamp);

        return result;
    }
}
