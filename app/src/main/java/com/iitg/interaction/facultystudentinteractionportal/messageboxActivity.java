package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class messageboxActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagebox);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        TextView subjecttv= findViewById(R.id.tv_msgbox_subject);
        TextView sendertv = findViewById(R.id.tv_sender);
        TextView receivertv = findViewById(R.id.tv_receiver);
        TextView datetv= findViewById(R.id.tv_datetime);
        TextView bodytv = findViewById(R.id.tv_body);
        Button deletebtn= findViewById(R.id.btn_deletemsg);
        Button replybtn = findViewById(R.id.btn_replymsg);
        Button forwardbtn= findViewById(R.id.btn_forwardmsg);


        Intent intent = getIntent();
        final String subject = intent.getStringExtra("subject");
        final String sender = intent.getStringExtra("sender");
        final String receiver = intent.getStringExtra("receiver");
        final String body = intent.getStringExtra("body");
        final String date = intent.getStringExtra("datetime");
        final int uniqueid =intent.getIntExtra("id",-1);


        Log.d("debug","uniquid at start msgboxactivity = "+uniqueid);

        subjecttv.setText(subject);
        sendertv.setText(sender);
        receivertv.setText(receiver);
        datetv.setText(date);
        bodytv.setText(body);

        replybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(),ComposeMessage.class);
                intent2.putExtra("sender",sender);
                intent2.putExtra("subject",subject);
                intent2.putExtra("replybtn",true);
                startActivity(intent2);

            }
        });

        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[]  arrdate= date.split(" ");
                Intent intent2 = new Intent(getApplicationContext(),ComposeMessage.class);
                intent2.putExtra("body","From: "+sender+"\nTo: "+receiver+"\nDate: "+arrdate[1]+" "+arrdate[2]+" "+arrdate[4]+"\n\n"+body);
                intent2.putExtra("subject",subject);
                intent2.putExtra("forward",true);
                startActivity(intent2);

            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("users");
                //ArrayList<Messages> msglist=UserInfo.messages;
                Log.d("debug","uniquid in clikclistener = "+uniqueid);

                Log.d("debug","msglis "+UserInfo.messages.size() + " "+uniqueid);
                if(uniqueid==-1)
                {
                    Toast.makeText(getApplicationContext(),"Not able to delete!",Toast.LENGTH_LONG).show();
                    return;
                }
                //msglist.remove(uniqueid);
                UserInfo.messages.remove(uniqueid);


//                for(Messages a : msglist)
//                {
//                    if(a.uniquid.equals(uniqueid))
//                        msglist.remove(a);
//
//                    break;
//                }
                dataref.child(UserInfo.username).child("messages").setValue(UserInfo.messages);

                Toast.makeText(getApplicationContext(),"Message Deleted Successfully",Toast.LENGTH_LONG).show();

//                Intent intent1 = new Intent(getApplicationContext(),MessageActivity.class);
//                startActivity(intent1);
                messageboxActivity.this.finish();
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
