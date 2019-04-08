package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TimeTable extends Activity {


   // private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();


    int i = 0;
    ArrayList<String> fruits = new ArrayList<>();

    private ListView mv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);

        DatabaseReference us = db.getReference().child("table");

        DatabaseReference ref = us;

        mv = (ListView) findViewById(R.id.results);


        final ArrayAdapter<String>  ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruits);

        mv.setAdapter(ad);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                fruits.add(value);
                ad.notifyDataSetChanged();
                i = fruits.size();
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

    private ArrayList<String> fruits2 = new ArrayList<>();

    private ArrayAdapter<String> ad2;

    public void addclick(View v){


        EditText b = (EditText) findViewById(R.id.task);

        DatabaseReference mRef =  db.getReference().child("table");
        mRef.child("task" + i).setValue(b.getText().toString());

        mv = (ListView) findViewById(R.id.results);
        mv.setAdapter(null);
        fruits2.clear();
        ad2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruits2);

        mv.setAdapter(ad2);

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String v = "" + fruits.size();
                Log.d("ramu", v);
                String value = dataSnapshot.getValue(String.class);
                fruits2.add(value);
                ad2.notifyDataSetChanged();
                i = fruits2.size();
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


       // mDatabase.child("user1").setValue(b.text);
        Button b1 = findViewById(R.id.addbutton);
        b1.setBackgroundColor(Color.parseColor("red"));



    }
}
