package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComposeMessage extends AppCompatActivity {

    EditText receiver,subject,body;
    FloatingActionButton sendbtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        receiver= findViewById(R.id.et_receiver);
        subject = findViewById(R.id.et_subject);
        body = findViewById(R.id.et_msgbody);
        sendbtn = findViewById(R.id.fab_sendmsg);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageFunc();
            }
        });

    }

    void sendMessageFunc()
    {
        if(receiver.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Senders username",Toast.LENGTH_LONG).show();
            return;
        }
        if(subject.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Subject",Toast.LENGTH_LONG).show();
            return;

        }
        if(body.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Write some message !",Toast.LENGTH_LONG).show();
            return;

        }
        if(body.getText().toString().length() > 750)
        {
            Toast.makeText(getApplicationContext(),"number of characters in body should not exceed 750.",Toast.LENGTH_LONG).show();
            return;
        }

        final GenericTypeIndicator<ArrayList<Messages>> t = new GenericTypeIndicator<ArrayList<Messages>>() {};

        final Messages newmsg = new Messages(UserInfo.username,receiver.getText().toString(),subject.getText().toString(),body.getText().toString());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(receiver.getText().toString()).exists())
                {
                    ArrayList<Messages> mlist = dataSnapshot.child(receiver.getText().toString()).child("messages").getValue(t);
                    if(mlist==null)
                    {
                        mlist = new ArrayList<>();
                    }
                    mlist.add(newmsg);
                    if(UserInfo.messages==null)
                    {
                        UserInfo.messages = new ArrayList<>();
                    }
                    UserInfo.messages.add(newmsg);
                    databaseReference.child(receiver.getText().toString()).child("messages").setValue(mlist);
                    databaseReference.child(UserInfo.username).child("messages").setValue(UserInfo.messages);
                    Toast.makeText(getApplicationContext(),"Message sent successfully!",Toast.LENGTH_LONG).show();
               //     ComposeMessage.this.finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),receiver.getText() + " is not registered on this app.",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        Intent intent = new Intent(ComposeMessage.this, MessageActivity.class);
        startActivity(intent);

        //this.finish();



    }

}
