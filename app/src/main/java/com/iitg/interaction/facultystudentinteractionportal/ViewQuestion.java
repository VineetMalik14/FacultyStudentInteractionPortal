package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewQuestion extends AppCompatActivity {

    ArrayList<member> list;
    DatabaseReference ref;
    RecyclerView viewFeedback;
    TextView title, endpage;
    FloatingActionButton btnNewTask;
    String Float;
    Adapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        //Toast.makeText(getApplicationContext(),"loading or not",Toast.LENGTH_LONG).show();

        title = findViewById(R.id.title);
        endpage = findViewById(R.id.endpage);
        btnNewTask = findViewById(R.id.btnNewTask);

        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference reff;
                reff = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Float = dataSnapshot.child("float").getValue(String.class);
                        if (Float == null) {
                            Intent a = new Intent(ViewQuestion.this, AddQuestion.class );
                            startActivity(a);
                        } else if (Float.equals("1")) {
                            //btnNewTask.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Feedback Form for this already exists", Toast.LENGTH_LONG).show();
                        } else {
                            Intent a = new Intent(ViewQuestion.this, AddQuestion.class );
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        Button button5;
        button5 = findViewById(R.id.Float);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
                ref.child("float").setValue("1");
                Toast.makeText(getApplicationContext(),"Feedback has been floated",Toast.LENGTH_LONG).show();
            }
        });

        viewFeedback = findViewById(R.id.viewFeedback);
        viewFeedback.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        //Toast.makeText(getApplicationContext(),"assigning or not",Toast.LENGTH_LONG).show();

        //FirebaseApp.initializeApp(this);
        ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID).child("FeedbackCourse");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    //Toast.makeText(getApplicationContext(),"getting or not",Toast.LENGTH_LONG).show();
                    member p = dataSnapshot1.getValue(member.class);
                    list.add(p);
                    //Toast.makeText(getApplicationContext(),p.getQuesName(),Toast.LENGTH_LONG).show();

                }
                if (list.size() != 0)
                {
                    Adapter = new Adapter(ViewQuestion.this, list);
                    viewFeedback.setAdapter(Adapter);
                    Adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}