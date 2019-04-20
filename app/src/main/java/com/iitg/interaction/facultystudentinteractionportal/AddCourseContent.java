package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCourseContent extends AppCompatActivity {

    private TextView CourseIDTextView;
    private TextView CourseNameTextView;
    private TextView KeyTextView;
    private TextView DescriptionTextView;
    private TextView SyllabusTextView;
    private TextView MarksTextView;
    private Button CourseAddbutton;
    private DatabaseReference databaseReference;
    public View dialogView;
    public Integer number;
    public String TextViewText;
    public String InDatabaseSlotsText=",";
    public Spinner spinner;
    int[] days=new int[5];
    String lastindex;
    public Midsemester midsemester;
    public Endsemester endsemester;
    public String courseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        courseid = getIntent().getStringExtra("CourseID");


        final DatabaseReference databaseReference_users_add_course = FirebaseDatabase.getInstance().getReference();
        UserInfo.courses.add(courseid);
        databaseReference_users_add_course.child("users").child(UserInfo.username).child("courses").setValue(UserInfo.courses);


        CourseIDTextView = findViewById(R.id.textView2);
        CourseNameTextView = findViewById(R.id.editText);
        KeyTextView = findViewById(R.id.editText4);
        DescriptionTextView = findViewById(R.id.editText2);
        SyllabusTextView = findViewById(R.id.editText3);
        MarksTextView = findViewById(R.id.editText5);
        CourseAddbutton = findViewById(R.id.button2);

        // getting the courseID from CourseAdd activity
        CourseIDTextView.setText(getIntent().getStringExtra("CourseID"));

        final TextView TimeSlotsTextview = findViewById(R.id.textView5);
        TimeSlotsTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddCourseContent.this);
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.add_time_slots_prof, null);
                dialogBuilder.setView(dialogView);
                 spinner = dialogView.findViewById(R.id.spinner);
//                dialogView.findViewById(R.id.id0).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id1).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id2).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id3).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id4).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id5).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id6).setVisibility(View.GONE);
                dialogView.findViewById(R.id.id7).setVisibility(View.GONE);
//                dialogView.findViewById(R.id.id);


                final AlertDialog b = dialogBuilder.create();
                b.show();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // your code here
                        number = Integer.parseInt(spinner.getSelectedItem().toString()) ;

                        if(1<=number)
                        {
                            dialogView.findViewById(R.id.id1).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id1).setVisibility((View.GONE));
                        }

                        if(2<=number)
                        {
                            dialogView.findViewById(R.id.id2).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id2).setVisibility((View.GONE));
                        }
                        if(3<=number)
                        {
                            dialogView.findViewById(R.id.id3).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id3).setVisibility((View.GONE));
                        }
                        if(4<=number)
                        {
                            dialogView.findViewById(R.id.id4).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id4).setVisibility((View.GONE));
                        }
                        if(5<=number)
                        {
                            dialogView.findViewById(R.id.id5).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id5).setVisibility((View.GONE));
                        }
                        if(6<=number)
                        {
                            dialogView.findViewById(R.id.id6).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id6).setVisibility((View.GONE));
                        }
                        if(7<=number)
                        {
                            dialogView.findViewById(R.id.id7).setVisibility(View.VISIBLE);
                        }
                        else{
                            dialogView.findViewById(R.id.id7).setVisibility((View.GONE));
                        }




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });
                days = new int[5];

                Button btn = dialogView.findViewById(R.id.button3);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        days[0]=0;days[1]=0;days[2]=0;days[3]=0;days[4]=0;
                        final TextView textView = findViewById(R.id.textView5);
                        textView.setText("");
                        InDatabaseSlotsText = ",";

                        if(number>=1)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay1);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration1);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime1);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";
                        }
                        if(number>=2)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay2);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration2);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime2);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";

                        }
                        if(number>=3)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay3);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration3);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime3);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";

                        }
                        if(number>=4)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay4);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration4);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime4);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";

                        }
                        if(number>=5)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay5);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration5);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime5);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";

                        }
                        if(number>=6)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay6);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration6);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime6);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";

                        }
                        if(number>=7)
                        {
                            Spinner spinner1 =  dialogView.findViewById(R.id.spinnerDay7);
                            Spinner spinner2 = dialogView.findViewById(R.id.spinnerDuration7);
                            Spinner spinner3 = dialogView.findViewById(R.id.spinnerTime7);
                            if(spinner1.getSelectedItem().toString().equals("Monday")){days[0]++;}
                            if(spinner1.getSelectedItem().toString().equals("Tuesday")){days[1]++;}
                            if(spinner1.getSelectedItem().toString().equals("Wednesday")){days[2]++;}
                            if(spinner1.getSelectedItem().toString().equals("Thursday")){days[3]++;}
                            if(spinner1.getSelectedItem().toString().equals("Friday")){days[4]++;}
                            TextViewText = spinner1.getSelectedItem().toString() + "    " + spinner2.getSelectedItem().toString() + "   " + spinner3.getSelectedItem().toString() + "\n";
                            textView.append(TextViewText);
                            InDatabaseSlotsText+=spinner1.getSelectedItem().toString() + "-" +  spinner3.getSelectedItem().toString() + "-" +  spinner2.getSelectedItem().toString() + ",";

                        }
                        if(days[0]>1 || days[1]>1 || days[2]>1 || days[3]>1 || days[4]>1){Toast.makeText(AddCourseContent.this,"You can't select two classes on same day.", Toast.LENGTH_LONG).show();textView.setText("");;InDatabaseSlotsText=",";}

                        b.dismiss();
                    }

                });
            }
        });

        //mid sem
        final TextView midSem_btn = findViewById(R.id.MidSem);
        midSem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddCourseContent.this);
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.midsem_alert_box, null);
                dialogBuilder.setView(dialogView);

                final AlertDialog b = dialogBuilder.create();
                b.show();

                // getting date
                //--------------------------------------------------
                final Calendar myCalendar = Calendar.getInstance();
                final EditText date_of_exam = dialogView.findViewById(R.id.DateExam);
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.HOUR,hour);
//                myCalendar.set(Calendar.MINUTE,minutes);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        date_of_exam.setText(sdf.format(myCalendar.getTime()));
                    }


                };

                date_of_exam.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        new DatePickerDialog(AddCourseContent.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                new DatePickerDialog(getActivity(),myCalendar.get(Calendar.DAY_OF_WEEK));
                    }
                });

                //-----------------------------------
                // show time select dialog box on clicking time edit box
                //------------------------------
                final EditText time_of_exam = dialogView.findViewById(R.id.TimeExam);
                time_of_exam.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

//                Calendar mcurrentTime = Calendar.getInstance();
                        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = myCalendar.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(AddCourseContent.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                time_of_exam.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });
                //-------------------------------------------------

                Button addmidsem_btn = dialogView.findViewById(R.id.AddExamButton);
                addmidsem_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = dialogView.findViewById(R.id.DescriptionExam);
                        EditText editText1 = dialogView.findViewById(R.id.DateExam);
                        EditText editText2 = dialogView.findViewById(R.id.TimeExam);
                        EditText editText3 = dialogView.findViewById(R.id.VenueExam);
                        EditText editText4 = dialogView.findViewById(R.id.DurationExam);

                        final String description = editText.getText().toString();
                        final String date = editText1.getText().toString();
                        final String time = editText2.getText().toString();
                        final String venue = editText3.getText().toString();
                        final String duration = editText4.getText().toString();


                        if(description.equals("") || date.equals("") || time.equals("") || venue.equals("") || duration.equals("")){Toast.makeText(AddCourseContent.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();}
                        else {
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                             midsemester = new Midsemester(date,duration,time,description,venue);
//                            String key=databaseReference.child("Courses").child(courseid).child("MidSemester").push().getKey();
//                            databaseReference.child("Courses").child(courseid).child("MidSemester").setValue(midsemester);
                            midSem_btn.setText("Mid Semester added");
                            b.dismiss();
                        }
                    }
                });

            }
        });

        // getting endsem


        final TextView endsem_btn = findViewById(R.id.EndSem);
        endsem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddCourseContent.this);
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.endsem_alert_box, null);
                dialogBuilder.setView(dialogView);

                final AlertDialog b = dialogBuilder.create();
                b.show();

                // getting date
                //--------------------------------------------------
                final Calendar myCalendar = Calendar.getInstance();
                final EditText date_of_exam = dialogView.findViewById(R.id.DateExam);
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.HOUR,hour);
//                myCalendar.set(Calendar.MINUTE,minutes);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        date_of_exam.setText(sdf.format(myCalendar.getTime()));
                    }


                };

                date_of_exam.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        new DatePickerDialog(AddCourseContent.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                new DatePickerDialog(getActivity(),myCalendar.get(Calendar.DAY_OF_WEEK));
                    }
                });

                //-----------------------------------
                // show time select dialog box on clicking time edit box
                //------------------------------
                final EditText time_of_exam = dialogView.findViewById(R.id.TimeExam);
                time_of_exam.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

//                Calendar mcurrentTime = Calendar.getInstance();
                        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = myCalendar.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(AddCourseContent.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                time_of_exam.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });
                //-------------------------------------------------

                Button addendsem_btn = dialogView.findViewById(R.id.AddExamButton);
                addendsem_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = dialogView.findViewById(R.id.DescriptionExam);
                        EditText editText1 = dialogView.findViewById(R.id.DateExam);
                        EditText editText2 = dialogView.findViewById(R.id.TimeExam);
                        EditText editText3 = dialogView.findViewById(R.id.VenueExam);
                        EditText editText4 = dialogView.findViewById(R.id.DurationExam);

                        final String description = editText.getText().toString();
                        final String date = editText1.getText().toString();
                        final String time = editText2.getText().toString();
                        final String venue = editText3.getText().toString();
                        final String duration = editText4.getText().toString();


                        if(description.equals("") || date.equals("") || time.equals("") || venue.equals("") || duration.equals("")){Toast.makeText(AddCourseContent.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();}
                        else {
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            endsemester = new Endsemester(date,duration,time,description,venue);
//                            String key=databaseReference.child("Courses").child(courseid).child("MidSemester").push().getKey();
//                            databaseReference.child("Courses").child(courseid).child("MidSemester").setValue(midsemester);
//                            databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).setValue("");
                            endsem_btn.setText("End Semester added");
                            b.dismiss();
                        }
                    }
                });

            }
        });




        // adding data to firebase
        //-----------------------
        CourseAddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textView = findViewById(R.id.editText);
                EditText textView1 = findViewById(R.id.editText2);
                EditText textView2 = findViewById(R.id.editText3);
                EditText textView3 = findViewById(R.id.editText4);
                EditText textView4 = findViewById(R.id.editText5);
                TextView textView5 = findViewById(R.id.textView5);
                if(textView.getText().toString().equals("") || textView1.getText().toString().equals("") || textView2.getText().toString().equals("") || textView3.getText().toString().equals("") || textView4.getText().toString().equals("") || textView5.getText().toString().equals("") || endsem_btn.getText().equals("") || midSem_btn.getText().equals(""))
                {
                    Toast.makeText(AddCourseContent.this, "None of the fields should be empty.", Toast.LENGTH_LONG).show();
                }
                else
                {
//                    Toast.makeText(AddCourseContent.this,InDatabaseSlotsText,Toast.LENGTH_LONG).show();
//                    Toast.makeText(AddCourseContent.this,midsemester.Description,Toast.LENGTH_LONG).show();
                    final Courses courses = new Courses(CourseIDTextView.getText().toString(),KeyTextView.getText().toString(),
                            DescriptionTextView.getText().toString(), Calendar.getInstance().getTime(),
                            CourseNameTextView.getText().toString(),MarksTextView.getText().toString()
                            ,UserInfo.username,SyllabusTextView.getText().toString(),InDatabaseSlotsText,midsemester,endsemester);
                    Log.d("fgf",midsemester.Description);
                    databaseReference = FirebaseDatabase.getInstance().getReference();
//                    databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful())
//                            {
                                databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).setValue(courses).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
//                                            databaseReference_users_add_course.child("users").child(UserInfo.username).child("courses").setValue(UserInfo.courses);
                                            databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).child("MidSemester").setValue(midsemester);
                                            databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).child("EndSemester").setValue(endsemester);
                                        }
                                    }
                                });
//                            }
//                        }
//                    });



                    // adding all the information in putExtra to show in course content page of prof where events and materials can also be added
                    //----------------------------------
                    Intent intent = new Intent(AddCourseContent.this, CourseMainPageStudent.class);
                    intent.putExtra("CourseID",CourseIDTextView.getText().toString());
                    intent.putExtra("CourseTitle",CourseNameTextView.getText().toString());
                    intent.putExtra("CourseDescription",DescriptionTextView.getText().toString());
                    intent.putExtra("CourseSyllabus",SyllabusTextView.getText().toString());
                    intent.putExtra("CourseMarks",MarksTextView.getText().toString());
                    intent.putExtra("CourseKey",KeyTextView.getText().toString());
                    intent.putExtra("CourseTimeSlots",textView5.getText().toString());
                    intent.putExtra("CourseDateOfCreation",Calendar.getInstance().getTime().toString());
                    CourseMainPageStudent.courseID = CourseIDTextView.getText().toString();
                    finish();
                    startActivity(intent);

                }

            }
        }
        );
        //-----------------------------------





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DatabaseReference delete1 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference delete2 = FirebaseDatabase.getInstance().getReference();
        delete1.child("users").child(UserInfo.username).child("courses").child(String.valueOf((UserInfo.courses.size()-1))).removeValue();
        delete2.child("Courses").child(courseid).removeValue();
        UserInfo.courses.remove(UserInfo.courses.size()-1);




    }
//
//    private AlertDialog AskOption()
//    {
//        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
//                .setTitle("Exit")
//                .setMessage("Are you sure you want to exit?")
////                .setIcon(R.drawable.delete)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                        courseid = CourseIDTextView.getText().toString();
//
//                        final ArrayList<String> courses = new ArrayList<String>();
//                        DatabaseReference databaseReference2 = databaseReference.child("users").child(UserInfo.username).child("courses");
//                        databaseReference2.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
//                                        String course = messageSnapshot.getValue().toString();
//                                        courses.add(course);
////                                        Log.v("Title", course.getTitle());
//                                    }
//                                DatabaseReference databaseReferencecourse = databaseReference.child("Courses").child(courseid);
//                                databaseReferencecourse.removeValue();
//                                courses.remove(courses.size()-1);
//                                databaseReference.child("users").child(UserInfo.username).child("courses").setValue(courses);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create();
//        return myQuittingDialogBox;
//
//    }

}
