package com.iitg.interaction.facultystudentinteractionportal;

public class CourseMaterial {
    private String Title;
    private String URL;
    private String FileName;

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

    public CourseMaterial(String title, String URL, String fileName) {
        Title = title;
        this.URL = URL;
        FileName = fileName;
    }
}
