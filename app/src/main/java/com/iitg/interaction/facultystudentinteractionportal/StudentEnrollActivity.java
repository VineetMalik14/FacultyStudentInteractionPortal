package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentEnrollActivity extends AppCompatActivity implements EnrollCourseStudent.EnrollDialogListener{

    TextView name;
    TextView desc;
    TextView time;
    TextView title;
    TextView syl;
    TextView prof;
    String marks;
    private String originalkey;
    String currentuser = UserInfo.username;
    private ArrayList<String> al;
    int check = 0;
    private String gotkey;
    long count =0;
    int temp = 0;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enroll);

//        Intent i = getIntent();
        name = findViewById(R.id.name);

        name.setText(getIntent().getStringExtra("name"));
        Log.v("fdjnd",name.getText().toString());
//
        DatabaseReference us = db.getReference().child("Courses").child(name.getText().toString()).child("description");
        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                desc = findViewById(R.id.descripton);
                desc.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });


        DatabaseReference po = db.getReference().child("Courses").child(name.getText().toString()).child("marksDistribution");
        po.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               marks = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });

        us = db.getReference().child("Courses").child(name.getText().toString()).child("fullname");
        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                title = findViewById(R.id.title);
                title.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        us = db.getReference().child("Courses").child(name.getText().toString()).child("professor");
        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                prof = findViewById(R.id.prof);
                prof.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        us = db.getReference().child("Courses").child(name.getText().toString()).child("syllabus");
        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                syl = findViewById(R.id.syllabus);
                syl.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        us = db.getReference().child("Courses").child(name.getText().toString()).child("timeSlots");
        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                time = findViewById(R.id.timeslots);
                time.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button b =(Button) findViewById(R.id.enroll);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UserInfo.usertype.equals("Stud"))
                {
                    EnrollCourseStudent ed = new EnrollCourseStudent();
                    ed.show(getSupportFragmentManager(), "Enroll Dialog");
                }
                else
                {
                    Toast.makeText(StudentEnrollActivity.this,"You are not allowed to enroll in this course",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void openDialog(){
        if(UserInfo.usertype.equals("Stud"))
        {
            EnrollCourseStudent ed = new EnrollCourseStudent();
            ed.show(getSupportFragmentManager(), "Enroll Dialog");
        }
        else
        {
            Toast.makeText(StudentEnrollActivity.this,"You are not allowed to enroll in this course",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void applyTexts(String key) {
        gotkey= key;
        DatabaseReference pk = db.getReference().child("Courses").child(name.getText().toString()).child("courseKey");
        pk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                originalkey = dataSnapshot.getValue(String.class);
                //name.setText(originalkey + " - " + gotkey );
                if(originalkey.equals(gotkey)){


                    //;
                    DatabaseReference am = db.getReference().child("users").child(currentuser);
                    am.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                            if(dataSnapshot.getKey().equals("courses")){
                                count = dataSnapshot.getChildrenCount();
                            }



                            if(temp == 0){
                                temp = 1;
                                //name.setText(name.getText().toString() + " - " + count);
                                DatabaseReference jkl = db.getReference().child("users").child(currentuser).child("courses");
                                jkl.child(Long.toString(count)).setValue(name.getText().toString());

                                Intent intent = new Intent(getApplicationContext(),CourseSearchTabbed.class);

                                UserInfo.courses.add(name.getText().toString());
                                CourseMainPageStudent.courseID= name.getText().toString();
//                                intent.putExtra("CourseID", CourseMainPageStudent.courseID);
//                                intent.putExtra("CourseTitle", title.getText());
//                                intent.putExtra("CourseDescription", desc.getText());
//                                intent.putExtra("CourseSyllabus", syl.getText());
//                                intent.putExtra("CourseMarks", marks);
//                                intent.putExtra("CourseTimeSlots", time.getText());

                                startActivity(intent);
                                finish();
                            }


                            //name.setText(name.getText().toString() + " - " + count );






                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //name.setText(originalkey);
        /*if(originalkey.equals(key)){
            DatabaseReference am = db.getReference().child(currentuser).child("courses");
            am.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    count = dataSnapshot.getChildrenCount();
                    String aj = "" + count;
                    name.setText(aj);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/
    }

}


