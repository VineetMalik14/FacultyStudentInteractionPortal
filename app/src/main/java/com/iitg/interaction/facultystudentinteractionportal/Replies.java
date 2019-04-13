package com.iitg.interaction.facultystudentinteractionportal;

import java.util.Date;

public class Replies {

    private Date DateOfCreation;
    private Date LastModified;
    private String ReplyContent;
    private String Username;
    private String Usertype;

    public Date getDateOfCreation() {
        return DateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }

    public Date getLastOfmodified() {
        return LastModified;
    }

    public void setLastOfmodified(Date lastOfmodified) {
        LastModified = lastOfmodified;
    }

    public String getReplyContent() {
        return ReplyContent;
    }

    public void setReplyContent(String replyContent) {
        ReplyContent = replyContent;
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


    public Replies() {
    }

    public Replies(Date dateOfCreation, Date lastOfmodified, String replyContent, String username, String usertype) {
        DateOfCreation = dateOfCreation;
        LastModified = lastOfmodified;
        ReplyContent = replyContent;
        Username = username;
        Usertype = usertype;
    }


}
