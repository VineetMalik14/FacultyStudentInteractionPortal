package com.iitg.interaction.facultystudentinteractionportal;

import java.sql.Time;
import java.util.Date;

public class Event {

    private Date DateOfCreation;
    private String Title;
    private String Description;
    private String DateOfEvent;
    private String TimeOfEvent;
    private String Venue;
    private String Type;


    public Event()
    {

    }

    public Event(Date dateOfCreation, String dateOfEvent, String title, String description, String type,String venue,String time) {
        DateOfCreation = dateOfCreation;
        DateOfEvent = dateOfEvent;
        Title = title;
        Description = description;
        Type = type;
        Venue = venue;
        TimeOfEvent = time;
    }

    public Date getDateOfCreation() {
        return DateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }

    public String getDateOfEvent() {
        return DateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
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

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getTimeOfEvent() {
        return TimeOfEvent;
    }

    public void setTimeOfEvent(String timeOfEvent) {
        TimeOfEvent = timeOfEvent;
    }
}
