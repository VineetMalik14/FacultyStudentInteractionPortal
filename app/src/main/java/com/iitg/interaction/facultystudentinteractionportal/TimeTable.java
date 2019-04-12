package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;



//TASKS
//If date already exists, then don't overwrite it.




public class TimeTable extends Activity implements DatePickerDialog.OnDateSetListener {


    // private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    Button b;
    TextView t;

    int day, month, year;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);



        b = findViewById(R.id.choose);
        t = findViewById(R.id.text_date);


        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpg = new DatePickerDialog(TimeTable.this, TimeTable.this, year, month, day);
                dpg.show();

            }

        });


    }

    @Override
    public void onDateSet(DatePicker datepicker, int a, int b, int c) {




        b = b+1;
        String ddd = c + "-" + b + "-" + a;
        t.setText(ddd);
        String x = "CS201";
        DatabaseReference us = db.getReference().child("Calendar");
        Day current = new Day(x,x,x,x,x,x,x,x,x,x,x,x,x);
        us.child(ddd).setValue(current);


        
    }
}


