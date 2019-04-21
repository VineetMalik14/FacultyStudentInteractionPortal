package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class CreateFeedBackForm extends AppCompatActivity {
    private DatabaseReference reff;

    public int num = 5;
    public CheckBox[] myTextViews = new CheckBox[num];
    public CheckBox[][] group = new CheckBox[num][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feed_back_form);
        Log.d("aaaaa","aaaaa");
        final String[] array = new String[num];
        final String[][] Options = new String[num][5];
        for (int i = 0;i<num;i++)
        {
            array[i] = new String();
            for (int j =  0;j<5;j++)
            {
                Options[i][j] = new String();
            }
        }
        array[0] = "What is the speed of the class?";
        array[1] = "What is the difficulty of the class?";
        array[2] = "Is the class interesting?";
        array[3] = "What is the speed of the class?";
        array[4] = "What is the speed of the class?";
        for (int i = 0;i<num;i++)
        {
            Options[i][0] = "Very less";
            Options[i][1] = "Less";
            Options[i][2] = "Moderate";
            Options[i][3] = "Highly";
            Options[i][4] = "Very highly";
        }

        for (int j = 0;j<num;j++)
        {
            RadioGroup ll = new RadioGroup(this);

            ll.setMinimumWidth(1400);
            ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            CheckBox textView= new CheckBox(this);
            textView.setTypeface(null,Typeface.BOLD);
            //textView.setTextSize(24);
            //textView.setMinimumHeight();
            //textView.setM
            textView.setLayoutParams(new LinearLayout.LayoutParams(             //select linearlayoutparam- set the width & height
                    ViewGroup.LayoutParams.MATCH_PARENT, 28));
            textView.setText(array[j]);
            textView.setSingleLine(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin=15;
            params.topMargin=15;

            //textView.
            ll.addView(textView,params);
            myTextViews[j] = textView;
            for(int i = 0;i<5;i++)
            {
                CheckBox rdbtn = new CheckBox(this);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText(Options[j][i]);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.leftMargin=50;
                //params2.topMargin=5;
                group[j][i] = rdbtn;
                ll.addView(rdbtn,params2);
            }
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
        }

        //Toast.makeText(getApplicationContext(),"Hello Javatpoint100",Toast.LENGTH_LONG).show();
        final Button button;
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"10",Toast.LENGTH_LONG).show();
                reff = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
                reff.child("float").setValue("0");
                //reff.child("FeedbackCourse");
                reff = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID).child("FeedbackCourse");
                //reff.child("count").setValue("0");
                int ran;
                //Toast.makeText(getApplicationContext(),Integer.toString(num),Toast.LENGTH_LONG).show();
                for (int i = 0;i<num;i++)
                {
                    //Toast.makeText(getApplicationContext(),"Hello Javatpoint0",Toast.LENGTH_LONG).show();
                    Log.d("debug","loop");
                    //button.setText(button.getText().toString() + " - " + Integer.toString(i));
                    if (myTextViews[i].isChecked()) {
                        //button.setText(button.getText().toString() + " - " + Integer.toString(i));
                        //Toast.makeText(getApplicationContext(),"Hello Javatpoint"+i,Toast.LENGTH_LONG).show();
                        Log.d("debug","loop"+i);
//                        reff.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Toast.makeText(getApplicationContext(),"Reading",Toast.LENGTH_LONG).show();
//                                FeedbackCourse newcount = dataSnapshot.getValue(FeedbackCourse.class);
//                                int val = newcount.getCount();
//                                val = val+1;
//                                reff.child("count").setValue(val);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                        ran = new Random().nextInt();
                        //Toast.makeText(getApplicationContext(),ran,Toast.LENGTH_LONG).show();
                        member Temp = new member();
                        //Toast.makeText(getApplicationContext(),Integer.toString(ran),Toast.LENGTH_LONG).show();
//                        reff.child("Question"+ran).child("QuesName").setValue(array[i]);
////                        Temp.setQuesName(myTextViews[i].getText().toString().trim());
//                        reff.child("Question"+ran).child("key").setValue(Integer.toString(ran));
//                        for(int j = 0;j<5;j++) {
//                            if (group[i][j].isChecked()) {
//                                //button.setText(button.getText().toString() + " - " + group[i][j].getText().toString());
//                                reff.child("Question" + ran).child("Op" + j + "Name").setValue(group[i][j].getText().toString());
//                                reff.child("Question" + ran).child("Op" + j + "value").setValue("0");
//
//                            }
//                        }

                        Temp.setQuesName(array[i]);
                        //Temp.setKey();

                        for (int j = 0; j < 5; j++) {

                            if (group[i][j].isChecked())
                            {
                                switch (j)
                                {
                                    case 0: Temp.setOp1Name(group[i][j].getText().toString().trim());
                                        break;
                                    case 1: Temp.setOp2Name(group[i][j].getText().toString().trim());
                                        break;
                                    case 2: Temp.setOp3Name(group[i][j].getText().toString().trim());
                                        break;
                                    case 3: Temp.setOp4Name(group[i][j].getText().toString().trim());
                                        break;
                                    case 4: Temp.setOp0Name(group[i][j].getText().toString().trim());
                                        break;

                                }
                            }
                        }
                        String key = reff.push().getKey();
                        Temp.setKey(key);
                        //reff.push().setValue(Temp);
                        reff.child(key).setValue(Temp);
                        //Toast.makeText(getApplicationContext(),key,Toast.LENGTH_LONG).show();



                    }

                }

                Intent intent =new Intent(CreateFeedBackForm.this,ViewQuestion.class);
                startActivity(intent);
                finish();
            }
        });
        Button button2;
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddQuestion.class);
                startActivity(intent);
                //  Log.d("qwerty","qwerty");
            }
        });
    }
}
