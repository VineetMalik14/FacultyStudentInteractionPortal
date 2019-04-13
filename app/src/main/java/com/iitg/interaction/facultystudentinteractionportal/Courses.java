package com.iitg.interaction.facultystudentinteractionportal;

import java.util.ArrayList;
import java.util.Date;

public class Courses {

    private String CourseID;
    private String CourseKey;
    private String Description;
    private Date DateOfCreation;
    private ArrayList<Event> events;
    private String Fullname;
    private String MarksDistribution;
    private String Professor;
    private String StudentList;
    private String Syllabus;
    private String TimeSlots;
    private ArrayList<Thread> threads;

    public Courses() {
    }

    public Courses(String courseID, String courseKey, String description, Date dateOfCreation, ArrayList<Event> events, String fullname, String marksDistribution, String professor, String studentList, String syllabus, String timeSlots, ArrayList<Thread> threads) {
        CourseID = courseID;
        CourseKey = courseKey;
        Description = description;
        DateOfCreation = dateOfCreation;
        this.events = events;
        Fullname = fullname;
        MarksDistribution = marksDistribution;
        Professor = professor;
        StudentList = studentList;
        Syllabus = syllabus;
        TimeSlots = timeSlots;
        this.threads = threads;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getCourseKey() {
        return CourseKey;
    }

    public void setCourseKey(String courseKey) {
        CourseKey = courseKey;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getDateOfCreation() {
        return DateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        DateOfCreation = dateOfCreation;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getMarksDistribution() {
        return MarksDistribution;
    }

    public void setMarksDistribution(String marksDistribution) {
        MarksDistribution = marksDistribution;
    }

    public String getProfessor() {
        return Professor;
    }

    public void setProfessor(String professor) {
        Professor = professor;
    }

    public String getStudentList() {
        return StudentList;
    }

    public void setStudentList(String studentList) {
        StudentList = studentList;
    }

    public String getSyllabus() {
        return Syllabus;
    }

    public void setSyllabus(String syllabus) {
        Syllabus = syllabus;
    }

    public String getTimeSlots() {
        return TimeSlots;
    }

    public void setTimeSlots(String timeSlots) {
        TimeSlots = timeSlots;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Thread> threads) {
        this.threads = threads;
    }
}
