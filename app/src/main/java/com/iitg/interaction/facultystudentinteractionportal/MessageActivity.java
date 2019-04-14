package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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
        lv = findViewById(R.id.lv_messages);
        composebtn = findViewById(R.id.fab_composemsg);

        composebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ComposeMessage.class);
                startActivity(intent);
            }
        });

        final GenericTypeIndicator<ArrayList<Messages>> t = new GenericTypeIndicator<ArrayList<Messages>>() {};






        dataref.child(UserInfo.username).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Messages> mlist ;
                Log.d("debug","Inside messagelist update ");
                mlist = dataSnapshot.getValue(t);
                UserInfo.messages=mlist;
                Log.d("debug","mlist got updated! ");
                try
                {
                    if( mlist!=null && !mlist.isEmpty())
                    {
                        MessageListAdaptor messageListAdaptor = new MessageListAdaptor(MessageActivity.this,R.layout.layout_messagecard,mlist);
                        lv.setAdapter(messageListAdaptor);
                    }
                }
                catch (NullPointerException e)
                {
                    Log.d("debug",e.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
