package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class PollsActivity extends AppCompatActivity {
    ListView lv;
    RelativeLayout pollrl,polloptionrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls);

        lv = findViewById(R.id.lv_pollsmainlist);
        pollrl = findViewById(R.id.rl_poll);
        polloptionrl =findViewById(R.id.rl_polloption);

    }

}


