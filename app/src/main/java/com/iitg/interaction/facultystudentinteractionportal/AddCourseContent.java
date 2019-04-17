package com.iitg.interaction.facultystudentinteractionportal;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String courseid = getIntent().getStringExtra("CourseID");
        UserInfo.courses.add(courseid);

        final DatabaseReference databaseReference_users_add_course = FirebaseDatabase.getInstance().getReference();
        databaseReference_users_add_course.child("users").child(UserInfo.username).child("courses").setValue(UserInfo.courses);
//        databaseReference_users_add_course.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot childsnapshot: dataSnapshot.getChildren()){
//                    lastindex = ((childsnapshot.getKey()));
//                }
//                databaseReference_users_add_course.child("Users").child("yupp").child(""+(lastindex+1)).setValue(getIntent().getStringExtra("CourseID"));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        databaseReference_users_add_course.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                long count = dataSnapshot.getChildrenCount();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                databaseReference.child("Users").child("yupp").child("courses").child(Long.toString(count)).setValue(getIntent().getStringExtra("CourseID"));
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
                if(textView.getText().toString().equals("") || textView1.getText().toString().equals("") || textView2.getText().toString().equals("") || textView3.getText().toString().equals("") || textView4.getText().toString().equals("") || textView5.getText().toString().equals(""))
                {
                    Toast.makeText(AddCourseContent.this, "None of the feilds should be empty.", Toast.LENGTH_LONG).show();
                }
                else
                {
//                    Toast.makeText(AddCourseContent.this,InDatabaseSlotsText,Toast.LENGTH_LONG).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    Courses courses = new Courses(CourseIDTextView.getText().toString(),KeyTextView.getText().toString(),
                            DescriptionTextView.getText().toString(), Calendar.getInstance().getTime(),
                            CourseNameTextView.getText().toString(),MarksTextView.getText().toString()
                            ,UserInfo.username,SyllabusTextView.getText().toString(),null,InDatabaseSlotsText,null);

                    databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).setValue(courses);

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

}
