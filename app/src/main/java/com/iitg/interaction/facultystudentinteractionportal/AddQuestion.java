package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddQuestion extends AppCompatActivity {
    DatabaseReference ref;
    Integer ran = new Random().nextInt();
    String Float;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        final EditText question;
        question = findViewById(R.id.question);
        final EditText option1;
        option1 = findViewById(R.id.option1);
        final EditText option2;
        option2 = findViewById(R.id.option2);
        final EditText option3;
        option3 = findViewById(R.id.option3);
        final EditText option4;
        option4 = findViewById(R.id.option4);
        final EditText option5;
        option5 = findViewById(R.id.option5);
        final Button button3;
        button3 = findViewById(R.id.button3);
        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float = dataSnapshot.child("float").getValue(String.class);
                if (Float == null) {
//                    Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
//                    startActivity(intent);
                } else if (Float.equals("1")) {
                    button3.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Feedback Form for this already exists", Toast.LENGTH_LONG).show();
                } else {
//                    Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
//                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
                ref.child("float").setValue("0");
                ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID).child("FeedbackCourse");
                //Toast.makeText(getApplicationContext(),Integer.toString(ran),Toast.LENGTH_LONG).show();
                ran = new Random().nextInt();
                //Toast.makeText(getApplicationContext(),ran,Toast.LENGTH_LONG).show();
                member Temp = new member();

                Temp.setQuesName(question.getText().toString());
                //Temp.setKey();

                for (int j = 0; j < 5; j++) {

                    if(!(option1.getText().toString().isEmpty()))
                    {
                        Temp.setOp1Name(option1.getText().toString());
                    }
                    if(!(option2.getText().toString().isEmpty()))
                    {
                        Temp.setOp2Name(option2.getText().toString());
                    }
                    if(!(option3.getText().toString().isEmpty()))
                    {
                        Temp.setOp3Name(option3.getText().toString());
                    }
                    if(!(option4.getText().toString().isEmpty()))
                    {
                        Temp.setOp4Name(option4.getText().toString());
                    }
                    if(!(option5.getText().toString().isEmpty()))
                    {
                        Temp.setOp0Name(option5.getText().toString());
                    }

                }
                String key = ref.push().getKey();
                Temp.setKey(key);
                //reff.push().setValue(Temp);
                ref.child(key).setValue(Temp);

                question.setText("");
                option1.setText("");
                option2.setText("");
                option3.setText("");
                option4.setText("");
                option5.setText("");
            }
        });


        Button view;
        view = findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestion.this, ViewQuestion.class);
                startActivity(intent);
            }
        });

    }
}
