
package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.Calendar;
import java.util.Collections;

public class ThreadReplies extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    String username = UserInfo.username;
    String type = UserInfo.usertype;
    EditText input;
    ArrayList<String> ids;
    ArrayList<Replies> replies;

    ArrayList<Replies> replies2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTitle("Chats" );
        setContentView(R.layout.activity_thread_replies);


        final String idofthread = getIntent().getExtras().getString("KEY");
        final String idofcourse = getIntent().getExtras().getString("COURSE");
        String title = getIntent().getExtras().getString("TITLE");
        String content = getIntent().getExtras().getString("CONTENT");
        String user = getIntent().getExtras().getString("USER");  // person who create the thread
        String time = getIntent().getExtras().getString("TIME");
        boolean threadclosed = getIntent().getExtras().getBoolean("COURSECLOSED");



        TextView contentview = findViewById(R.id.Contentthread);
        TextView titleview = findViewById(R.id.Titlethread);



        // make the gui add the user and time in the title text view
        contentview.setText(content);
        titleview.setText(title);

        // now we have set the value for the whole



        FloatingActionButton newreply = findViewById(R.id.newreply);
        input = findViewById(R.id.input);
        if (threadclosed){
            newreply.hide();
            input.setVisibility(View.INVISIBLE);

        }





        newreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (input.getText().toString().equals("")){
                    Toast.makeText(ThreadReplies.this, "Please enter a non empty reply", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Replies r = new Replies(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),input.getText().toString(),username, type);





                replies2 = new ArrayList<Replies>();

                databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Courses").child(idofcourse).child("threads").child(idofthread).child("repliesArrayList");
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // ids = new ArrayList<String>();
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            //   ids.add(messageSnapshot.getKey());
                            //   ids.add(messageSnapshot.getKey());
                            Replies reply = messageSnapshot.getValue(Replies.class);
                            replies2.add(reply);
                            //  Log.v("Title", event.getTitle());
                        }
                        replies2.add(r);

                        databaseReference2.setValue(replies2);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                input.setText("");

            }
        });






        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(idofcourse).child("threads").child(idofthread).child("repliesArrayList");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                replies = new ArrayList<Replies>();
                //     ids = new ArrayList<String>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    //           ids.add(messageSnapshot.getKey());
                    //           ids.add(messageSnapshot.getKey());
                    Replies reply = messageSnapshot.getValue(Replies.class);
                    replies.add(reply);
                    //  Log.v("Title", event.getTitle());
                }

                Collections.reverse(replies);


                final ThreadReplies.ReplyAdapter adapter = new ThreadReplies.ReplyAdapter(ThreadReplies.this, replies);

                ListView listView = (ListView) findViewById(R.id.lv_Replies);
                listView.setAdapter(adapter);


                // now we will add the replies





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }





    public class ReplyAdapter extends ArrayAdapter<Replies> {
        public ReplyAdapter(Context context, ArrayList<Replies> replies) {
            super(context, 0, replies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Replies user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            // Lookup view for data population
            TextView messagetext = (TextView) convertView.findViewById(R.id.reply_text);
            TextView messageuser = (TextView) convertView.findViewById(R.id.reply_user);
            TextView messageTime = (TextView) convertView.findViewById(R.id.reply_time);



            // Populate the data into the template view using the data object
            messagetext.setText(user.getReplyContent());
            messageuser.setText(user.getUsername());

            messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", user.getDateOfCreation()));
            // Return the completed view to render on screen
            return convertView;
        }
    }


}