package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Events extends AppCompatActivity {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    ArrayList<String> list_today = new ArrayList<String>();
    ArrayList<String> list_upcoming = new ArrayList<String>();
    ListView today;
    String formattedDate;
    Context ye;
    List<HashMap<String, String>> listItems;
    List<HashMap<String, String>> listdo;
    String currentuser = "barney";
    HashMap<String, String> nameAddresses;
    HashMap<String, String> namedo;
    ListView upcoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        today = findViewById(R.id.table_today);
        upcoming = findViewById(R.id.table_upcoming);

        list_today.clear();
        list_upcoming.clear();
        ye = this;

        /*final ArrayAdapter<String>  ad_today = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_today);
        final ArrayAdapter<String>  ad_upcoming = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_upcoming);*/

        nameAddresses = new HashMap<>();
        namedo = new HashMap<>();
        /*nameAddresses.put("Diana", "3214 Broadway Avenue");
        nameAddresses.put("Tyga", "343 Rack City Drive");
        nameAddresses.put("Rich Homie Quan", "111 Everything Gold Way");
        nameAddresses.put("Donna", "789 Escort St");
        nameAddresses.put("Bartholomew", "332 Dunkin St");
        nameAddresses.put("Eden", "421 Angelic Blvd");*/

        listItems = new ArrayList<>();
        listdo = new ArrayList<>();

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(d);
        //Toast.makeText(getApplicationContext(),formattedDate,Toast.LENGTH_SHORT).show();

        DatabaseReference getthem = db.getReference().child("users").child(currentuser).child("courses");
        getthem.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                DatabaseReference getevents = db.getReference().child("Courses").child(value).child("Events");
                getevents.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren())
                        {

                            String event  = data.child("title").getValue().toString();
                            String time = data.child("timeOfEvent").getValue().toString();
                            String ed = data.child("dateOfEvent").getValue().toString();
                            //Toast.makeText(getApplicationContext(),event + " - " + time,Toast.LENGTH_SHORT).show();
                            if(ed.equals(formattedDate)){
                                //Toast.makeText(getApplicationContext(),"equal chal rha h",Toast.LENGTH_SHORT).show();
                                nameAddresses.put(event, time);
                            }else{
                                namedo.put(event, ed);
                            }




                        }
                        SimpleAdapter ad_today = new SimpleAdapter(ye, listItems, R.layout.list_item_events,
                                new String[]{"First Line", "Second Line"},
                                new int[]{R.id.text1, R.id.text2});

                        SimpleAdapter ad_upcoming = new SimpleAdapter(ye, listdo, R.layout.list_item_events,
                                new String[]{"First Line", "Second Line"},
                                new int[]{R.id.text1, R.id.text2});


                        Iterator it = nameAddresses.entrySet().iterator();
                        listItems.clear();
                        while (it.hasNext())
                        {

                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry)it.next();
                            resultsMap.put("First Line", pair.getKey().toString());
                            resultsMap.put("Second Line", pair.getValue().toString());
                            listItems.add(resultsMap);
                        }

                        Iterator itdo = namedo.entrySet().iterator();
                        listdo.clear();
                        while (itdo.hasNext())
                        {

                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry)itdo.next();
                            resultsMap.put("First Line", pair.getKey().toString());
                            resultsMap.put("Second Line", pair.getValue().toString());
                            listdo.add(resultsMap);
                        }



                        today.setAdapter(ad_today);
                        upcoming.setAdapter(ad_upcoming);

                        ad_today.notifyDataSetChanged();
                        ad_upcoming.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
}
