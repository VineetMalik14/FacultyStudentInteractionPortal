package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseAdd extends AppCompatActivity {

    public static final String TAG = "CourseAdd";
    private DatabaseReference databaseReference;
    public String course_id;
    public int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        // main work starts here
        // first check the courseid enterred y the prof if it exists prompt him to add new one
        // else open a new intent where all the course information is present

        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                flag=0;
                EditText courseID = findViewById(R.id.CourseID);
                course_id = courseID.getText().toString();
                if (course_id.equals("")){
                    Toast.makeText(CourseAdd.this, "Enter a valid course key", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseReference.child("Courses").orderByKey().equalTo(course_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
//                            courseID.setText("");
                            if (flag == 0) {
                                Log.d(TAG, course_id);
                                Toast.makeText(CourseAdd.this, "This course ID already exists. Please enter new ID.", Toast.LENGTH_SHORT).show();
                                button.setEnabled(true);
                            }
                        }
                        else
                        {

                            // the below method shows how will you insert a child ina json tree , here you are inserting a child named  course id with the the keys set to null

                            // String key = databaseReference.child("Courses").push().getKey();


                            databaseReference.child("Courses").child(course_id).setValue("");
                            Log.d(TAG,course_id);
                            Intent intent = new Intent(CourseAdd.this, AddCourseContent.class);
                            intent.putExtra("CourseID",course_id);
                            finish();
                            startActivity(intent);
                            flag = 1;


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

//        String key=databaseReference.push().getKey();
//        databaseReference.child("Courses").child(key).setValue("courses");
    }

}
