package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        CourseIDTextView = findViewById(R.id.textView2);
        CourseNameTextView = findViewById(R.id.editText);
        KeyTextView = findViewById(R.id.editText4);
        DescriptionTextView = findViewById(R.id.editText2);
        SyllabusTextView = findViewById(R.id.editText3);
        MarksTextView = findViewById(R.id.editText5);
        CourseAddbutton = findViewById(R.id.button2);

        // getting the courseID from CourseAdd activity
        CourseIDTextView.setText(getIntent().getStringExtra("CourseID"));


        // adding data to firebase
        //-----------------------
        CourseAddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                Courses courses = new Courses(CourseIDTextView.getText().toString(),KeyTextView.getText().toString(),
                        DescriptionTextView.getText().toString(), Calendar.getInstance().getTime(),
                        CourseNameTextView.getText().toString(),MarksTextView.getText().toString(),"ABCD",SyllabusTextView.getText().toString());

                databaseReference.child("Courses").child(CourseIDTextView.getText().toString()).setValue(courses);
            }
        }
        );
        //-----------------------------------

        // adding all the information in putExtra to show in course content page of prof where events and materials can also be added
        //----------------------------------
        Intent intent = new Intent(AddCourseContent.this, CourseMainPageProf.class);
        intent.putExtra("CourseID",CourseIDTextView.getText().toString());
        intent.putExtra("CourseTitle",CourseNameTextView.getText().toString());
        intent.putExtra("CourseDescription",DescriptionTextView.getText().toString());
        intent.putExtra("CourseSyllabus",SyllabusTextView.getText().toString());
        intent.putExtra("CourseMarks",MarksTextView.getText().toString());
        intent.putExtra("CourseKey",KeyTextView.getText().toString());
        startActivity(intent);



    }

}
