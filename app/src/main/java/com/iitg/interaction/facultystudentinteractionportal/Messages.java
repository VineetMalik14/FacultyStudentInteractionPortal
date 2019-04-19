package com.iitg.interaction.facultystudentinteractionportal;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ServerValue;

import java.sql.Date;
import java.util.Calendar;

public class Messages {
    public String sender;
    public String receiver;
    public boolean read;
    public String subject;
    public String date;
    public String time;
    public String body;
    public String uniquid;

    public Messages()
    {

    }

    public Messages(String sender, String receiver, String subject, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.read = false;
        this.subject = subject;
        this.body = body;
        this.date = Calendar.getInstance().getTime().toString().replace("GMT+05:30","");
        this.uniquid = String.valueOf(Calendar.getInstance().getTimeInMillis())+sender;
    }
}
