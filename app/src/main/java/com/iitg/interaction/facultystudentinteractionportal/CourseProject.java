package com.iitg.interaction.facultystudentinteractionportal;

import java.util.Date;

public class CourseProject {
    private String Title;
    private String URL;
    private String FileName;

    private String Deadline;
    private String Description;


    public CourseProject(String title, String URL, String fileName,String deadline, String description) {
        Title = title;
        this.URL = URL;
        FileName = fileName;

        Deadline = deadline;
        Description = description;
    }

    public CourseProject() {
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public String getURL() {
        return URL;
    }

    public String getFileName() {
        return FileName;
    }


    public String getDeadline() {
        return Deadline;
    }

    public String getDescription() {
        return Description;
    }
}
