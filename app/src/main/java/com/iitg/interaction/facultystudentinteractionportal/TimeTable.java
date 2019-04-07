package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimeTable extends Activity {


    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);


    }

    public void addclick(View v){

        mDatabase.child("tasks").child("1").setValue("take it");
        Button b1 = findViewById(R.id.addbutton);
        b1.setBackgroundColor(Color.parseColor("red"));

    }
}
