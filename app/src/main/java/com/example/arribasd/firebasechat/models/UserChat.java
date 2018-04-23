package com.example.arribasd.firebasechat.models;

import java.util.HashMap;
import java.util.Map;

public class UserChat {
    String id;
    String with;

    public UserChat(String id, String with) {
        this.id = id;
        this.with = with;
    }

    public UserChat() {
        id = "";
        with = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("with", with);
        return result;
    }
}
