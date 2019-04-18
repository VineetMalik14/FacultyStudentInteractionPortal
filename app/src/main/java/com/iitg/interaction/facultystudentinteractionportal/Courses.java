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
    private ArrayList<CourseMaterial> CourseMaterial;
    private Midsemester midsemester;
    private Endsemester endsemester;

    public Courses(String courseID, String courseKey, String description, Date dateOfCreation, ArrayList<Event> events, String fullname, String marksDistribution, String professor, String studentList, String syllabus, String timeSlots, ArrayList<Thread> threads,ArrayList<CourseMaterial> courseMaterial,String exams) {
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
        this.CourseMaterial = courseMaterial;


    }

//    public Courses(String courseID, String courseKey, String description, Date dateOfCreation, String fullname, String marksDistribution, String professor, String syllabus,ArrayList<Event> events,String timeSlots,String threads,Midsemester midsemester,Endsemester endsemester) {
//        CourseID = courseID;
//        CourseKey = courseKey;
//        Description = description;
//        DateOfCreation = dateOfCreation;
//        Fullname = fullname;
//        MarksDistribution = marksDistribution;
//        Professor = professor;
//        Syllabus = syllabus;
//        TimeSlots = timeSlots;
//        midsemester = midsemester;
//        endsemester = endsemester;
////        this.events = new ArrayList<>();
//    }


    public Courses(String courseID, String courseKey, String description, Date dateOfCreation, String fullname, String marksDistribution, String professor, String syllabus, String timeSlots,Midsemester midsemester,Endsemester endsemester) {
        CourseID = courseID;
        CourseKey = courseKey;
        Description = description;
        DateOfCreation = dateOfCreation;
        Fullname = fullname;
        MarksDistribution = marksDistribution;
        Professor = professor;
        Syllabus = syllabus;
        TimeSlots = timeSlots;
        this.midsemester = midsemester;
        this.endsemester = endsemester;
    }

    public Courses(){

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

    public ArrayList<com.iitg.interaction.facultystudentinteractionportal.CourseMaterial> getCourseMaterial() {
        return CourseMaterial;
    }

    public void setCourseMaterial(ArrayList<com.iitg.interaction.facultystudentinteractionportal.CourseMaterial> courseMaterial) {
        CourseMaterial = courseMaterial;
    }


}
