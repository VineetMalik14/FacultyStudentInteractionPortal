package com.iitg.interaction.facultystudentinteractionportal;

import com.iitg.interaction.facultystudentinteractionportal.Courses;

import java.util.ArrayList;

public class NewUser {
    private boolean verified;
    public  String fullname;
    public  String username;
    public  String usertype;
    public  String rollnumber;
    public  String email;
    public  String occupation;
    public  String department;
    public  String year;
    public  String password;
    //public  ArrayList<Messages> messages;
    public ArrayList<String> courses;

    public NewUser()
    {

    }

    public NewUser(String username,String fullname, String usertype, String rollnumber, String email, String occupation, String department, String year,String password) {
        this.username = username;
        this.fullname = fullname;
        this.usertype = usertype;
        this.rollnumber = rollnumber;
        this.email = email;
        this.occupation = occupation;
        this.department = department;
        this.year = year;
        this.password = password;
        this.courses = new ArrayList<>();
        //this.messages = new ArrayList<>();
    }

    public boolean isVerified() {
        return verified;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public String getEmail() {
        return email;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getDepartment() {
        return department;
    }

    public String getYear() {
        return year;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }
}
