package com.iitg.interaction.facultystudentinteractionportal;

public class messages {
    String id;
    String text;
    String user;
    String date;

    public messages(String id,String text, String user, String date) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }
}
