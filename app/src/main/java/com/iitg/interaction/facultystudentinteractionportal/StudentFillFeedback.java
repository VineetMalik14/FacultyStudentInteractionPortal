//package com.example.myapplication;
package com.iitg.interaction.facultystudentinteractionportal;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StudentFillFeedback extends AppCompatActivity {

    ArrayList<member> list = new ArrayList<>();
    List<List<Integer>>options = new ArrayList<>();
    List<Integer>selected = new ArrayList<>();
    List<Integer>selectedradio = new ArrayList<>();
    RadioGroup radioGroup;
    TextView test;
    DatabaseReference ref;
    Button next;
    Button prev;
    String concat = "";

    int val;
    int i = 0;
    int h;
    int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fill_feedback);

        list.clear();
        options.clear();
        selected.clear();
        selectedradio.clear();
        //test = findViewById(R.id.tester);
        //test.setText("");
        ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID).child("FeedbackCourse");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    // Toast.makeText(getApplicationContext(), "getting or not", Toast.LENGTH_LONG).show();
                    member p = dataSnapshot1.getValue(member.class);
                    list.add(p);
                    List<Integer> blah = new ArrayList<>();
                    for (int u = 0;u<5;u++)
                    {
                        blah.add(10);
                    }
                    options.add(blah);
                    selected.add(10);
                    selectedradio.add(10);
                    // Toast.makeText(getApplicationContext(),p.getQuesName(),Toast.LENGTH_LONG).show();

                }
                //for (int i = 0;i<list.size();i++) {
                if (i<list.size()) {
                    TextView question;
                    question = findViewById(R.id.question);
                    question.setText(list.get(i).getQuesName());
                    //String concat = test.getText()+" "+Integer.toString(i);

//                        //first field
//                        //int k = 0;
                    int flag = 0;
                    RadioButton option1;
                    option1 = findViewById(R.id.option1);
                    if (!(list.get(i).getOp0Name().isEmpty())) {
                        options.get(i).set(0, 0);
                        option1.setText(list.get(i).getOp0Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 1;
                        flag = 1;
                    }
                    if (flag == 0 && !(list.get(i).getOp1Name().isEmpty())) {
                        // concat = test.getText() + " 1 " + list.get(i).getOp1Name();
                        options.get(i).set(0, 1);
                        option1.setText(list.get(i).getOp1Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 2;
                    }
                    if (flag == 0 && !(list.get(i).getOp2Name().isEmpty())) {
                        //concat = test.getText() + " 2 " + list.get(i).getOp2Name();
                        options.get(i).set(0, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && !(list.get(i).getOp3Name().isEmpty())) {
                        //concat = test.getText() + " 3 " + list.get(i).getOp3Name();
                        options.get(i).set(0, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && !(list.get(i).getOp4Name().isEmpty())) {
                        //concat = test.getText() + " 4 " + list.get(i).getOp4Name();
                        options.get(i).set(0, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //second field
//                        //k = 0;
                    flag = 0;
                    option1 = findViewById(R.id.option2);
                    if (k < 2 && !(list.get(i).getOp1Name().isEmpty())) {
                        options.get(i).set(1, 1);
                        option1.setText(list.get(i).getOp1Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 2;
                    }
                    if (flag == 0 && k < 3 && !(list.get(i).getOp2Name().isEmpty())) {
                        options.get(i).set(1, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(1, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(1, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //field 3
//                        //second field
//                        //k = 0;
                    flag = 0;
                    option1 = findViewById(R.id.option3);
                    if (k < 3 && !(list.get(i).getOp2Name().isEmpty())) {
                        options.get(i).set(2, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(2, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(2, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //field 4
//                        //second field
//                        //k = 0;
                    option1 = findViewById(R.id.option4);
                    flag = 0;
                    if (k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(3, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(3, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }

                    //field 5
                    //second field
                    //k = 0;
                    option1 = findViewById(R.id.option5);
                    if (k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(4, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                    }
                }
                //}
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
        next = findViewById(R.id.next);
        radioGroup = findViewById(R.id.radiogroup);
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            next.setEnabled(false);
        }
//        if (i==0)
//        {
//            prev.setEnabled(false);
//        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //checkedId is the RadioButton selected
                switch(checkedId)
                {
                    case R.id.option1:
                        //enable or disable button
                        selectedradio.set(i,1);
                        break;
                    case R.id.option2:
                        selectedradio.set(i,2);
                        //enable or disable button
                        break;
                    case R.id.option3:
                        selectedradio.set(i,3);
                        //enable or disable button
                        break;
                    case R.id.option4:
                        selectedradio.set(i,4);
                        //enable or disable button
                        break;
                    case R.id.option5:
                        selectedradio.set(i,5);
                        //enable or disable button
                        break;
                }
                RadioButton option1,option2,option3,option4,option5;
                option1 = findViewById(R.id.option1);
                if (option1.isChecked())
                {
                    selected.set(i,options.get(i).get(0));
                }
                option2 = findViewById(R.id.option2);
                if (option2.isChecked())
                {
                    selected.set(i,options.get(i).get(1));
                }
                option3 = findViewById(R.id.option3);
                if (option3.isChecked())
                {
                    selected.set(i,options.get(i).get(2));
                }
                option4 = findViewById(R.id.option4);
                if (option4.isChecked())
                {
                    selected.set(i,options.get(i).get(3));
                }
                option5 = findViewById(R.id.option5);
                if (option5.isChecked())
                {
                    selected.set(i,options.get(i).get(4));
                }
                //concat = Integer.toString(selected.get(i));
                //test.setText(concat);

                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    next.setEnabled(false);
                }
                else{
                    next.setEnabled(true);
                }
            }
        });
        //Toast.makeText(getApplicationContext(),Integer.toString(selected.get(i)),Toast.LENGTH_SHORT).show();
        //Integer.toString(selectedradio.get(i)),
        prev = findViewById(R.id.prev);
        if (i==0)
        {
            prev.setEnabled(false);
        }
        if (i <= 0){
            prev.setEnabled(false);
        }
        if (i>= list.size()-1)
        {
            next.setEnabled(false);
        }
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton button1;
                button1 = findViewById(R.id.option1);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option2);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option3);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option4);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option5);
                button1.setVisibility(View.INVISIBLE);
                i--;
                if (i<=0)
                {
                    prev.setEnabled(false);
                }
                if (i>=0 && i < list.size()) {
                    TextView question;
                    question = findViewById(R.id.question);
                    question.setText(list.get(i).getQuesName());
                    //first field
                    //int k = 0;
                    k=0;
                    int flag = 0;
                    RadioButton option1;
                    option1 = findViewById(R.id.option1);
                    if (!(list.get(i).getOp0Name().isEmpty())) {
                        options.get(i).set(0, 0);
                        option1.setText(list.get(i).getOp0Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 1;
                        flag = 1;
                    }
                    if (flag == 0 && !(list.get(i).getOp1Name().isEmpty())) {
                        //Toast.makeText(getApplicationContext(), "getting or not1", Toast.LENGTH_LONG).show();
                        // concat = test.getText() + " 1 " + list.get(i).getOp1Name();
                        options.get(i).set(0, 1);
                        option1.setText(list.get(i).getOp1Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 2;
                    }
                    if (flag == 0 && !(list.get(i).getOp2Name().isEmpty())) {
                        //concat = test.getText() + " 2 " + list.get(i).getOp2Name();
                        options.get(i).set(0, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && !(list.get(i).getOp3Name().isEmpty())) {
                        //concat = test.getText() + " 3 " + list.get(i).getOp3Name();
                        options.get(i).set(0, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && !(list.get(i).getOp4Name().isEmpty())) {
                        //concat = test.getText() + " 4 " + list.get(i).getOp4Name();
                        options.get(i).set(0, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //second field
//                        //k = 0;
                    flag = 0;
                    option1 = findViewById(R.id.option2);
                    if (k < 2 && !(list.get(i).getOp1Name().isEmpty())) {
                        options.get(i).set(1, 1);
                        option1.setText(list.get(i).getOp1Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 2;
                    }
                    if (flag == 0 && k < 3 && !(list.get(i).getOp2Name().isEmpty())) {
                        options.get(i).set(1, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(1, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(1, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //field 3
//                        //second field
//                        //k = 0;
                    flag = 0;
                    option1 = findViewById(R.id.option3);
                    if (k < 3 && !(list.get(i).getOp2Name().isEmpty())) {
                        options.get(i).set(2, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(2, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(2, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //field 4
//                        //second field
//                        //k = 0;
                    option1 = findViewById(R.id.option4);
                    flag = 0;
                    if (k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(3, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(3, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }

                    //field 5
                    //second field
                    //k = 0;
                    option1 = findViewById(R.id.option5);
                    if (k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(4, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                    }
                }

                radioGroup = findViewById(R.id.radiogroup);
                if (selectedradio.get(i)==10)
                {
                    radioGroup.clearCheck();
                }
                else{
                    RadioButton option1;
                    switch(selectedradio.get(i))
                    {
                        case 1: option1 = findViewById(R.id.option1); option1.setChecked(true);
                            break;
                        case 2: option1 = findViewById(R.id.option2); option1.setChecked(true);
                            break;
                        case 3: option1 = findViewById(R.id.option3); option1.setChecked(true);
                            break;
                        case 4: option1 = findViewById(R.id.option4); option1.setChecked(true);
                            break;
                        case 5: option1 = findViewById(R.id.option5); option1.setChecked(true);
                            break;
                    }
                }
                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    next.setEnabled(false);
                }
//                RadioGroup radioGroup;
//                radioGroup = findViewById(R.id.radiogroup);
//                if (i >= list.size() - 1 || radioGroup.getCheckedRadioButtonId() == -1) {
//                    next.setEnabled(false);
//                }
//                if (i < list.size() - 1) {
//                    next.setEnabled(true);
//                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        //checkedId is the RadioButton selected
                        switch(checkedId)
                        {
                            case R.id.option1:
                                //enable or disable button
                                selectedradio.set(i,1);
                                break;
                            case R.id.option2:
                                selectedradio.set(i,2);
                                //enable or disable button
                                break;
                            case R.id.option3:
                                selectedradio.set(i,3);
                                //enable or disable button
                                break;
                            case R.id.option4:
                                selectedradio.set(i,4);
                                //enable or disable button
                                break;
                            case R.id.option5:
                                selectedradio.set(i,5);
                                //enable or disable button
                                break;
                        }
                        RadioButton option1;
                        option1 = findViewById(R.id.option1);
                        if (option1.isChecked())
                        {
                            selected.set(i,options.get(i).get(0));
                        }
                        option1 = findViewById(R.id.option2);
                        if (option1.isChecked())
                        {
                            selected.set(i,options.get(i).get(1));
                        }
                        option1 = findViewById(R.id.option3);
                        if (option1.isChecked())
                        {
                            selected.set(i,options.get(i).get(2));
                        }
                        option1 = findViewById(R.id.option4);
                        if (option1.isChecked())
                        {
                            selected.set(i,options.get(i).get(3));
                        }
                        option1 = findViewById(R.id.option5);
                        if (option1.isChecked())
                        {
                            selected.set(i,options.get(i).get(4));
                        }
                        if (radioGroup.getCheckedRadioButtonId() == -1)
                        {
                            next.setEnabled(false);
                        }
                        else{
                            next.setEnabled(true);
                        }
                    }
                });
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //concat = "";
                if (next.getText().equals("Submit") || i == list.size()) {
                    RadioGroup Group;
                    Group = findViewById(R.id.radiogroup);
                    Group.setVisibility(View.INVISIBLE);
                    prev.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                    TextView question;
                    question = findViewById(R.id.question);
                    question.setText("Thank you for your valuable feedback");
                    for (h = 0; h < list.size(); h++) {
                        String trash = list.get(h).getKey();
                        switch (selected.get(h)) {
                            case 0:
                                val = list.get(h).getOp0value();
                                ref.child(trash).child("op0value").setValue(val + 1);
                                break;
                            case 1:
                                val = list.get(h).getOp1value();
                                ref.child(trash).child("op1value").setValue(val + 1);
                                break;
                            case 2:
                                val = list.get(h).getOp2value();
                                ref.child(trash).child("op2value").setValue(val + 1);
                                break;
                            case 3:
                                val = list.get(h).getOp3value();
                                ref.child(trash).child("op3value").setValue(val + 1);
                                break;
                            case 4:
                                val = list.get(h).getOp4value();
                                ref.child(trash).child("op4value").setValue(val + 1);
                                break;
                        }
                    }
                    ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID);
                    ref.child("Submitted").child(UserInfo.username).setValue(UserInfo.username);
                    Toast.makeText(getApplicationContext(),"Thank You for your valuable feedback.",Toast.LENGTH_LONG).show();
                    Intent a = new Intent(StudentFillFeedback.this, pieChart.class );
                    startActivity(a);
                    finish();
                }
                RadioButton button1;

                button1 = findViewById(R.id.option1);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option2);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option3);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option4);
                button1.setVisibility(View.INVISIBLE);
                button1 = findViewById(R.id.option5);
                button1.setVisibility(View.INVISIBLE);
                i++;
                if (i == list.size() - 1) {
                    next.setText("Submit");
                } else {
                    next.setText("Next");
                }
//                if (i>= list.size())
//                {
//                    next.setEnabled(false);
//                }
                if (i < list.size()) {
                    TextView question;
                    question = findViewById(R.id.question);
                    question.setText(list.get(i).getQuesName());
                    //first field
                    k = 0;
                    int flag = 0;
                    RadioButton option1;
                    option1 = findViewById(R.id.option1);
                    if (!(list.get(i).getOp0Name().isEmpty())) {
                        options.get(i).set(0, 0);
                        option1.setText(list.get(i).getOp0Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 1;
                        flag = 1;
                    }
                    if (flag == 0 && !(list.get(i).getOp1Name().isEmpty())) {
                        // concat = test.getText() + " 1 " + list.get(i).getOp1Name();
                        options.get(i).set(0, 1);
                        option1.setText(list.get(i).getOp1Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 2;
                    }
                    if (flag == 0 && !(list.get(i).getOp2Name().isEmpty())) {
                        //concat = test.getText() + " 2 " + list.get(i).getOp2Name();
                        options.get(i).set(0, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && !(list.get(i).getOp3Name().isEmpty())) {
                        //concat = test.getText() + " 3 " + list.get(i).getOp3Name();
                        options.get(i).set(0, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && !(list.get(i).getOp4Name().isEmpty())) {
                        //concat = test.getText() + " 4 " + list.get(i).getOp4Name();
                        options.get(i).set(0, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //second field
//                        //k = 0;
                    flag = 0;
                    option1 = findViewById(R.id.option2);
                    if (k < 2 && !(list.get(i).getOp1Name().isEmpty())) {
                        options.get(i).set(1, 1);
                        option1.setText(list.get(i).getOp1Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 2;
                    }
                    if (flag == 0 && k < 3 && !(list.get(i).getOp2Name().isEmpty())) {
                        options.get(i).set(1, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(1, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(1, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //field 3
//                        //second field
//                        //k = 0;
                    flag = 0;
                    option1 = findViewById(R.id.option3);
                    if (k < 3 && !(list.get(i).getOp2Name().isEmpty())) {
                        options.get(i).set(2, 2);
                        option1.setText(list.get(i).getOp2Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 3;
                    }
                    if (flag == 0 && k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(2, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(2, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }


//                        //field 4
//                        //second field
//                        //k = 0;
                    option1 = findViewById(R.id.option4);
                    flag = 0;
                    if (k < 4 && !(list.get(i).getOp3Name().isEmpty())) {
                        options.get(i).set(3, 3);
                        option1.setText(list.get(i).getOp3Name());
                        option1.setVisibility(View.VISIBLE);
                        flag = 1;
                        k = 4;
                    }
                    if (flag == 0 && k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(3, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                        k = 5;
                    }

                    //field 5
                    //second field
                    //k = 0;
                    option1 = findViewById(R.id.option5);
                    if (k < 5 && !(list.get(i).getOp4Name().isEmpty())) {
                        options.get(i).set(4, 4);
                        option1.setText(list.get(i).getOp4Name());
                        option1.setVisibility(View.VISIBLE);
                    }
                    //}
                    radioGroup = findViewById(R.id.radiogroup);
                    if (selectedradio.get(i) == 10) {
                        radioGroup.clearCheck();
                    } else {
                        RadioButton optionx;
                        switch (selectedradio.get(i)) {
                            case 1:
                                optionx = findViewById(R.id.option1);
                                optionx.setChecked(true);
                                break;
                            case 2:
                                optionx = findViewById(R.id.option2);
                                optionx.setChecked(true);
                                break;
                            case 3:
                                optionx = findViewById(R.id.option3);
                                optionx.setChecked(true);
                                break;
                            case 4:
                                optionx = findViewById(R.id.option4);
                                optionx.setChecked(true);
                                break;
                            case 5:
                                optionx = findViewById(R.id.option5);
                                optionx.setChecked(true);
                                break;
                        }
                    }
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            //checkedId is the RadioButton selected
                            switch (checkedId) {
                                case R.id.option1:
                                    //enable or disable button
                                    selectedradio.set(i, 1);
                                    break;
                                case R.id.option2:
                                    selectedradio.set(i, 2);
                                    //enable or disable button
                                    break;
                                case R.id.option3:
                                    selectedradio.set(i, 3);
                                    //enable or disable button
                                    break;
                                case R.id.option4:
                                    selectedradio.set(i, 4);
                                    //enable or disable button
                                    break;
                                case R.id.option5:
                                    selectedradio.set(i, 5);
                                    //enable or disable button
                                    break;
                            }
                            RadioButton option1;
                            option1 = findViewById(R.id.option1);
                            if (option1.isChecked()) {
                                selected.set(i, options.get(i).get(0));
                            }
                            option1 = findViewById(R.id.option2);
                            if (option1.isChecked()) {
                                selected.set(i, options.get(i).get(1));
                            }
                            option1 = findViewById(R.id.option3);
                            if (option1.isChecked()) {
                                selected.set(i, options.get(i).get(2));
                            }
                            option1 = findViewById(R.id.option4);
                            if (option1.isChecked()) {
                                selected.set(i, options.get(i).get(3));
                            }
                            option1 = findViewById(R.id.option5);
                            if (option1.isChecked()) {
                                selected.set(i, options.get(i).get(4));
                            }

                            if (radioGroup.getCheckedRadioButtonId() == -1) {
                                next.setEnabled(false);
                            } else {
                                next.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });

    }
}
