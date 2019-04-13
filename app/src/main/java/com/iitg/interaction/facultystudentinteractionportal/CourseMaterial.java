package com.iitg.interaction.facultystudentinteractionportal;

import java.util.Date;

public class CourseMaterial {
    private String Title;
    private String URL;
    private String FileName;
    private java.util.Date Date;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public CourseMaterial(String title, String URL, String fileName, Date date) {
        Title = title;
        this.URL = URL;
        FileName = fileName;
        Date = date;
    }
}
