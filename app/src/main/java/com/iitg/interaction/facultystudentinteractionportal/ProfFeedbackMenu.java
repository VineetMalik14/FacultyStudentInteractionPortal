package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfFeedbackMenu extends AppCompatActivity {
    String Float;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_feedback_menu);
        Button create, view,form;
        create = findViewById(R.id.create);
        view = findViewById(R.id.view);
        form = findViewById(R.id.form);
        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(),ViewQuestion.class);
                startActivity(a);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref;
                ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Float = dataSnapshot.child("float").getValue(String.class);
                        if (Float == null) {
                            Intent intent = new Intent(getApplicationContext(), CreateFeedBackForm.class);
                            startActivity(intent);
                        } else if (Float.equals("1")) {
                            Toast.makeText(getApplicationContext(), "Feedback Form for this already exists", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), CreateFeedBackForm.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), pieChart.class);
                startActivity(intent);
            }
        });
    }
}
