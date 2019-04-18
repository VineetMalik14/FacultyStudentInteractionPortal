package com.iitg.interaction.facultystudentinteractionportal;


import com.google.firebase.database.DatabaseReference;

public class Exams {

    public Endsemester endsemester;
    public Midsemester midsemester;

    public Exams(Endsemester endsemester, Midsemester midsemester) {
        this.endsemester = endsemester;
        this.midsemester = midsemester;
    }
    public Exams(){

    }


}
 class Endsemester{

    public String ExamDate;
    public String Duration;
    public String StartTime;
    public String Description;
    public String Venue;

     public Endsemester(String examDate, String duration, String startTime, String description, String venue) {
         ExamDate = examDate;
         Duration = duration;
         StartTime = startTime;
         Description = description;
         Venue = venue;
     }

     public Endsemester(){

     }




 }
 class Midsemester{
     public String ExamDate;
     public String Duration;
     public String StartTime;
     public String Description;
     public String Venue;

     public Midsemester(String examDate, String duration, String startTime, String description, String venue) {
         ExamDate = examDate;
         Duration = duration;
         StartTime = startTime;
         Description = description;
         Venue = venue;
     }

     public Midsemester(){

     }
 }


