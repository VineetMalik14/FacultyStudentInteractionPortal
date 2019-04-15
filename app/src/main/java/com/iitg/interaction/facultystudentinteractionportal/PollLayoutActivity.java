package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PollLayoutActivity extends AppCompatActivity {
    ListView lv;
    TextView question;
    String currentcourseid;
    int index;
    static Polls clickedpoll;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Courses");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutpoll);

        currentcourseid = CourseMainPageStudent.courseID;
        lv = findViewById(R.id.lv_optionlist);
        question = findViewById(R.id.tv_questionheading);

        final Intent intent = getIntent();
        index = intent.getIntExtra("index",-1);

        question.setText(clickedpoll.question);
        ArrayList<Options> optionlist = clickedpoll.options;
//        optionlist.add(new Options("This is option one"));
//        optionlist.add(new Options("This is option two"));
//        optionlist.add(new Options("This is option three"));
//        optionlist.add(new Options("This is option four"));
//        optionlist.add(new Options("This is option five"));


        PollListAdaptor pollListAdaptor = new PollListAdaptor(PollLayoutActivity.this,R.layout.layout_polloptions,optionlist);

        lv.setAdapter(pollListAdaptor);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
//                builder1.setMessage("Are you sure ?");
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent1 = getIntent();
//                                startActivity(intent);
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();

                clickedpoll.addvote(position,UserInfo.username);
                Intent intent1 = getIntent();
                startActivity(intent1);

            }
        });

        final GenericTypeIndicator<ArrayList<Polls>> t = new GenericTypeIndicator<ArrayList<Polls>>() {};

        dataref.child(currentcourseid).child("Polls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Polls> pollslist = dataSnapshot.getValue(t);

                if(pollslist!=null)
                {
                    pollslist.set(index,clickedpoll);
                    dataref.child(currentcourseid).child("Polls").setValue(pollslist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
