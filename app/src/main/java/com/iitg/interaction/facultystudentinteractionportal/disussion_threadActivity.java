package com.iitg.interaction.facultystudentinteractionportal;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class disussion_threadActivity extends AppCompatActivity {


    ListView ll ;
    Button query ;
    Button post ;
    EditText thread_content;
    EditText thread_title ;
    FirebaseDatabase database ;
    String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disussion_thread);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ll = (ListView) findViewById(R.id.lv_thread);
        query=findViewById(R.id.bt_newthread);
        post =findViewById(R.id.bt_post);
        thread_content=findViewById(R.id.et_post);
        thread_title = findViewById(R.id.et_postTitle);
        database = FirebaseDatabase.getInstance();


        Bundle extras = getIntent().getExtras();
        course =extras.getString("course");
        Toast.makeText(getApplicationContext(),"I Love you",Toast.LENGTH_LONG).show();







        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setVisibility(View.INVISIBLE);
                thread_content.setVisibility(View.VISIBLE);
                post.setVisibility(View.VISIBLE);
                thread_title.setVisibility(View.VISIBLE);
                query.setVisibility(View.INVISIBLE);

            }

        });
        String [] s ={};
        DatabaseReference mref =   database.getReference("Courses").child(course).child("Discussion");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren() )
                {


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String content = thread_content.getText().toString();
                String title= thread_title.getText().toString();
                Date c = Calendar.getInstance().getTime();
                ArrayList<Replies> repliesArrayList = new ArrayList<Replies>();
                final Thread newthread = new Thread(false,UserInfo.username,UserInfo.usertype,content,title,c,c,repliesArrayList);

                //ADD NEW thread Here

                /*//at palces of CS101 courses should be there
                DatabaseReference ref = database.getReference().child("Courses").child("CS101");
                Courses now;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        now = dataSnapshot.getValue(Courses .class);
                      ArrayList<Thread> threads =  now.getThreads();
                      if(threads == null)
                      {
                          ArrayList<Thread> newthread1 = new ArrayList<Thread>();
                          newthread1.add(newthread);
                          now.setThreads(newthread1);
                      }
                      else
                      {
                          threads.add(newthread);
                      }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });*/


                ll.setVisibility(View.VISIBLE);
                thread_content.setVisibility(View.INVISIBLE);
                post.setVisibility(View.INVISIBLE);
                thread_title.setVisibility(View.INVISIBLE);
                query.setVisibility(View.VISIBLE);


            }
        });









    }

}
