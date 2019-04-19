package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ListView lv;
    FloatingActionButton composebtn;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Messages");

        lv = findViewById(R.id.lv_messages);
        composebtn = findViewById(R.id.fab_composemsg);

        composebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ComposeMessage.class);
                startActivity(intent);
            }
        });

//        final GenericTypeIndicator<ArrayList<Messages>> t = new GenericTypeIndicator<ArrayList<Messages>>() {};



        final MessageListAdaptor messageListAdaptor = new MessageListAdaptor(MessageActivity.this,R.layout.layout_messagecard,UserInfo.messages);
        lv.setAdapter(messageListAdaptor);


        dataref.child(UserInfo.username).child("messages").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Messages> mlist ;
                Log.d("debug","Inside messagelist update ");
                if(UserInfo.messages==null)
                {
                    UserInfo.messages= new ArrayList<>();
                }

                UserInfo.messages.clear();
              for(DataSnapshot data : dataSnapshot.getChildren())
              {
                  Messages msg = data.getValue(Messages.class);
                  UserInfo.messages.add(msg);

              }

              Collections.reverse(UserInfo.messages);




              messageListAdaptor.notifyDataSetChanged();
                Log.d("debug","mlist got updated! ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
