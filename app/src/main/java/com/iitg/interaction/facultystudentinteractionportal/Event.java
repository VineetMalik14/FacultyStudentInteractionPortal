package com.iitg.interaction.facultystudentinteractionportal;

import java.util.Date;

public class Event {

    private Date DateOfCreation;
    private Date DateOfEvent;
    private String Title;
    private String Description;
    private String Type;

    public Event(Date dateOfCreation, Date dateOfEvent, String title, String description, String type) {
        DateOfCreation = dateOfCreation;
        DateOfEvent = dateOfEvent;
        Title = title;
        Description = description;
        Type = type;
    }

    public Date getDateOfCreation() {
        return DateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }

    public Date getDateOfEvent() {
        return DateOfEvent;
    }

    public void setDateOfEvent(Date dateOfEvent) {
        DateOfEvent = dateOfEvent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
