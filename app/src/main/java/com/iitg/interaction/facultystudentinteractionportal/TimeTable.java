package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;




//TASKS
//Good Testing





public class TimeTable extends Fragment implements DatePickerDialog.OnDateSetListener {


    // private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    Button b;
    TextView t;
    ListView r;
    ArrayList<String> al = new ArrayList<String>();


    int day, month, year;
    int day_week;
    String dayOfWeek;
    String currentuser = UserInfo.username;
    String sub_day;
    String value;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_time_table, container, false);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

//        Toast.makeText(getActivity(),"working",Toast.LENGTH_LONG).show();


        b = getView().findViewById(R.id.choose);
        t = getView().findViewById(R.id.text_date);


        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                day_week = c.get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dpg = new DatePickerDialog(getActivity(), TimeTable.this, year, month, day_week);
                dpg.show();

            }

        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker datepicker, int a, int b, int c) {


        b = b+1;
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(a, b-1, c-1);
        dayOfWeek = simpledateformat.format(date);
        sub_day = dayOfWeek.substring(0,3);
        String ddd = c + "-" + b + "-" + a ;
        t.setText(ddd);
        r = (ListView) getView().findViewById(R.id.result);
        al.clear();

        final ArrayAdapter<String>  ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al);
        r.setAdapter(ad);
        ad.notifyDataSetChanged();




        //String x = "CS201";
        //Day current = new Day(x,x,x,x,x,x,x,x,x,x,x,x,x);
        // us.child(ddd).setValue(current);

        DatabaseReference us = db.getReference().child("users").child(currentuser).child("courses");
        us.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                value = dataSnapshot.getValue(String.class);


                DatabaseReference in = db.getReference().child("Courses").child(value).child("timeSlots");
                /*String prev =t.getText().toString();
                t.setText(prev + "\n" + value);*/
                filltable(in, ad, value);


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

    public void filltable(DatabaseReference in, final ArrayAdapter<String> ad , final String xxx){
        in.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String op = dataSnapshot.getValue(String.class);

                if(op != null){
                            /*String prev =t.getText().toString();
                            t.setText(prev + "\n" + op);*/
                    String[] split =  op.split(",");
                    int i = 0;
                    for(i = 1; i< split.length; i++){
                        String check = split[i].substring(0,3);
                        if(check.equals(sub_day)){
                                    /*String prev =t.getText().toString();
                                    t.setText(prev + "\n" + value + " " + split[i] );*/

                            String[] split2 =  split[i].split("-");

                            int begin = Integer.parseInt(split2[1]);
                            int dur = Integer.parseInt(split2[2]);
                            int j = 0;
                            for(j = 0; j< dur; j++){
                                int temp = begin + j;
                                String ans = "" + temp + " " + xxx;
                                al.add(ans);

                                ad.notifyDataSetChanged();

                            }
                            Collections.sort(al);
                            //ad.notifyDataSetChanged();

                        }
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


