package com.iitg.interaction.facultystudentinteractionportal;

import java.util.ArrayList;
import java.util.Date;

public class Thread {

    private boolean IsThreadClosed;
    private String Username;
    private String Usertype;
    private String ThreadContent;
    private String Title;
    private Date DateOfCreation;
    private Date LastModified;
    private ArrayList<Replies> repliesArrayList;


    public Thread(boolean isThreadClosed, String username, String usertype, String threadContent, String title, Date dateOfCreation, Date lastModified, ArrayList<Replies> repliesArrayList) {
        IsThreadClosed = isThreadClosed;
        Username = username;
        Usertype = usertype;
        ThreadContent = threadContent;
        Title = title;
        DateOfCreation = dateOfCreation;
        LastModified = lastModified;
        this.repliesArrayList = repliesArrayList;
    }

    public boolean isThreadClosed() {
        return IsThreadClosed;
    }

    public void setThreadClosed(boolean threadClosed) {
        IsThreadClosed = threadClosed;
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

    public ArrayList<Replies> getRepliesArrayList() {
        return repliesArrayList;
    }

    public void setRepliesArrayList(ArrayList<Replies> repliesArrayList) {
        this.repliesArrayList = repliesArrayList;
    }
}
