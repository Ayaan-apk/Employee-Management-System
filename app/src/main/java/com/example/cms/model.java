package com.example.cms;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class model implements Serializable {
    String Date;
    String Reason;
    String Username;
    String Status;
    @Exclude private String id;

    public model() {
    }

    public model(String date, String reason, String username,String status) {
        Date = date;
        Reason = reason;
        Username = username;
        Status = status;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
