package com.example.cms;

public class mymodel {
    String date,username,status;

    public mymodel() {
    }

    public mymodel(String date, String username, String status) {
        this.date = date;
        this.username = username;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
