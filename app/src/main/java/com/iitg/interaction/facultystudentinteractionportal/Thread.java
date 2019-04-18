package com.iitg.interaction.facultystudentinteractionportal;

import java.util.ArrayList;
import java.util.Date;

public class Thread {

    private boolean ThreadClosed;
    private String Username;
    private String Usertype;
    private String ThreadContent;
    private String Title;
    private Date DateOfCreation;
    private Date LastModified;

    public Thread(){

    }

    public boolean isThreadClosed() {
        return ThreadClosed;
    }

    public void setThreadClosed(boolean threadClosed) {
        ThreadClosed = threadClosed;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(String usertype) {
        Usertype = usertype;
    }

    public String getThreadContent() {
        return ThreadContent;
    }

    public void setThreadContent(String threadContent) {
        ThreadContent = threadContent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getDateOfCreation() {
        return DateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }

    public Date getLastModified() {
        return LastModified;
    }

    public void setLastModified(Date lastModified) {
        LastModified = lastModified;
    }


    public Thread(boolean threadClosed, String username, String usertype, String threadContent, String title, Date dateOfCreation, Date lastModified) {
        ThreadClosed = threadClosed;
        Username = username;
        Usertype = usertype;
        ThreadContent = threadContent;
        Title = title;
        DateOfCreation = dateOfCreation;
        LastModified = lastModified;
    }
}
