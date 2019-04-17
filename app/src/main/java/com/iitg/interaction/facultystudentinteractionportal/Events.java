package com.iitg.interaction.facultystudentinteractionportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Events extends AppCompatActivity {


    ArrayList<String> list_today = new ArrayList<String>();
    ArrayList<String> list_upcoming = new ArrayList<String>();
    ListView today;
    ListView upcoming;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        today = findViewById(R.id.table_today);
        upcoming = findViewById(R.id.table_upcoming);

        list_today.clear();
        list_upcoming.clear();

        /*final ArrayAdapter<String>  ad_today = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_today);
        final ArrayAdapter<String>  ad_upcoming = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_upcoming);*/

        HashMap<String, String> nameAddresses = new HashMap<>();
        nameAddresses.put("Diana", "3214 Broadway Avenue");
        nameAddresses.put("Tyga", "343 Rack City Drive");
        nameAddresses.put("Rich Homie Quan", "111 Everything Gold Way");
        nameAddresses.put("Donna", "789 Escort St");
        nameAddresses.put("Bartholomew", "332 Dunkin St");
        nameAddresses.put("Eden", "421 Angelic Blvd");

        List<HashMap<String, String>> listItems = new ArrayList<>();

        SimpleAdapter ad_today = new SimpleAdapter(this, listItems, R.layout.list_item_events,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }



        today.setAdapter(ad_today);
        //upcoming.setAdapter(ad_upcoming);

        ad_today.notifyDataSetChanged();
        //ad_upcoming.notifyDataSetChanged();





    }
}
