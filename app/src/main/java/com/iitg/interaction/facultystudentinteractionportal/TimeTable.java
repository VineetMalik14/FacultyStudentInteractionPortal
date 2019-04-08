package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private int i = 0;

    private ListView mv;
    int oops = 0;

    private ArrayList<String> fruits = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);

        DatabaseReference us = db.getReference().child("table");

        DatabaseReference ref = us;


        mv = (ListView) findViewById(R.id.results);

        final ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruits );
        mv.setAdapter(ad);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Toast.makeText(getApplicationContext(), Integer.toString(oops) ,Toast.LENGTH_SHORT).show();
                long j = dataSnapshot.getChildrenCount();

                for(long i = 1; i <= j; i++){
                    Toast.makeText(getApplicationContext(),Integer.toString(1) ,Toast.LENGTH_SHORT).show();
                    oops+=1;
                    Log.d("DEBUG ",Integer.toString(oops));
                    //String index =  "task" + i;
                    //int oops = (int) i;
                    /*StringBuilder s = new StringBuilder(100);
                    s.append("task");
                    s.append(oops);*/
                    //String p = oops.toString();
                    Toast.makeText(getApplicationContext(),"task" ,Toast.LENGTH_SHORT).show();
                    String index = "task" +""+ Integer.toString(   oops );
                   // Toast.makeText("task" +  Integer.toString(oops));
                    //Toast.makeText(getApplicationContext(),"task2" ,Toast.LENGTH_SHORT).show();
                   //' String nmn = s.toString();
                    String value  = dataSnapshot.child(index).getValue(String.class);
                    fruits.add(value);
                    ad.notifyDataSetChanged();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addclick(View v){

        i++;
        EditText b = (EditText) findViewById(R.id.task);

        DatabaseReference mRef =  db.getReference().child("table");
        mRef.child("task" + i).setValue(b.getText().toString());


       // mDatabase.child("user1").setValue(b.text);
        Button b1 = findViewById(R.id.addbutton);
        b1.setBackgroundColor(Color.parseColor("red"));



    }
}
