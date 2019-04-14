package com.iitg.interaction.facultystudentinteractionportal;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreatePollActivity extends AppCompatActivity {

    int numberOfLines = 0;
    String currentcourse="CS101";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(currentcourse);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);
        Button addoptionbtn = findViewById(R.id.btn_createpoll_addoption);
        Button submitbtn = findViewById(R.id.btn_createpoll_finish);

        EditText ett = findViewById(R.id.et_option1);
        Log.d("debug"," option 1 tag = "+ett.getTag());
        addoptionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line();

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitfunc();

            }
        });

    }

    public void Add_Line() {
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_createpoll);
        // add edittext
        EditText et = new EditText(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(p);
        et.setHint("Option "+ (numberOfLines+1));
        et.setId(numberOfLines+1);
        //et.setTag(numberOfLines+1);
        Log.d("debug"," id "+ et.getId());
        ll.addView(et);
        numberOfLines++;
    }

    void submitfunc()
    {
        EditText question = findViewById(R.id.et_createpoll_question);
        EditText option1 = findViewById(R.id.et_option1);
        EditText option2 = findViewById(R.id.et_option2);

        ArrayList<String> optionlist= new ArrayList<>();
        if(!option1.getText().toString().isEmpty())
        {
            optionlist.add(option1.getText().toString());
        }

        if(!option2.getText().toString().isEmpty())
        {
            optionlist.add(option2.getText().toString());
        }


        for(int i = 1;i<=numberOfLines;i++)
        {
            @SuppressLint("ResourceType") EditText et = findViewById(i);
            if(!et.getText().toString().isEmpty())
            {
                optionlist.add(et.getText().toString());
            }

        }

        if(optionlist.size()<2)
        {
            Toast.makeText(getApplicationContext(),"Enter atleast two Options!",Toast.LENGTH_LONG).show();
            return;
        }

        final GenericTypeIndicator<ArrayList<Polls>> t = new GenericTypeIndicator<ArrayList<Polls>>() {};

        final Polls newpoll = new Polls(question.getText().toString(),optionlist);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Polls> pollslist;
                pollslist = dataSnapshot.child("Polls").getValue(t);
                if(pollslist==null)
                {
                    pollslist = new ArrayList<>();
                }
                pollslist.add(newpoll);

                databaseReference.child("Polls").setValue(pollslist);

                Toast.makeText(getApplicationContext(),"Poll added successfully!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
