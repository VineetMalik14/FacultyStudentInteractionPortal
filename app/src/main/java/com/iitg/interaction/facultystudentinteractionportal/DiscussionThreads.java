package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


//import com.firebase.ui.auth.AuthUI;
//import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ValueEventListener;


public class DiscussionThreads extends AppCompatActivity {
    //FloatingActionButton newthread;
    private DatabaseReference databaseReference;
    View dialogView;

    String course = "CS101";
    List<String> ids;
    List<Thread> threads;
    String username = "Annanaya";
    String usertype = "Student";
    ThreadAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        setTitle("Discussion Forum CS204" );




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_threads);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(course).child("threads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                threads = new ArrayList<Thread>();
                ids = new ArrayList<String>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    ids.add(messageSnapshot.getKey());
                    Thread thread = messageSnapshot.getValue(Thread.class);
                    threads.add(thread);
                    //  Log.v("Title", event.getTitle());
                }
                Collections.reverse(threads);
                adapter = new ThreadAdapter(DiscussionThreads.this, threads);

                ListView listView = (ListView) findViewById(R.id.lv_thread);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // now we have  all the value that will be needed for
                        Thread item = adapter.getItem(position);
                        String key = ids.get(position);
                        // now send the key with the intent you are showing
                        Intent intent = new Intent(getBaseContext(), ThreadReplies.class);
                        intent.putExtra("KEY", key);
                        intent.putExtra("COURSE", course);
                        intent.putExtra("TITLE", item.getTitle());
                        intent.putExtra("CONTENT", item.getThreadContent());
                        intent.putExtra("USER", item.getUsername());
                        intent.putExtra("TIME", DateFormat.format("dd-MM-yyyy (HH:mm:ss)", item.getLastModified()) );
                        

                        startActivity(intent);




                        // now we have the item in the
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





















        FloatingActionButton newthread =  findViewById(R.id.new_thread);

        newthread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DiscussionThreads.this);
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.add_thread_dialog_box, null);
                dialogBuilder.setView(dialogView);

                final AlertDialog b = dialogBuilder.create();
                b.setTitle("Add New Thread");
                b.show();

                Button addthread =  dialogView.findViewById(R.id.buttonAddThread);
                addthread.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        EditText title = dialogView.findViewById(R.id.Title);
                        EditText content = dialogView.findViewById(R.id.Content);


                        Date c = Calendar.getInstance().getTime();

                        ArrayList<Replies> repliesArrayList = new ArrayList<Replies>();
                        if (title.getText().toString().equals("") || content.getText().toString().equals("")) {
                            Toast.makeText(DiscussionThreads.this, "Please enter correct Title and Content", Toast.LENGTH_SHORT).show();
                        } else {
                            Thread newthread = new Thread(false, username, usertype, content.getText().toString(), title.getText().toString(), c, c, repliesArrayList);
                            databaseReference = FirebaseDatabase.getInstance().getReference();



                            String id = databaseReference.child("Courses").child(course).child("threads").push().getKey();
                            databaseReference.child("Courses").child(course).child("threads").child(id).setValue(newthread);
                            b.dismiss();

                        }


                    }
                });


            }
        });


        // show all the threads in a list view adaptor

        ListView listOfMessage = (ListView)findViewById(R.id.lv_thread);
//        adapter = new FirebaseListAdapter<Thread>(this,Thread.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference())
//        {
//
//        }






//        adapter = new FirebaseListAdapter<Thread>(this,Thread.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference().child("Courses").child(course).child("threads")) {
//            @Override
//            protected void populateView(View v, Thread model, int position) {
//                //TextView messageText, messageUser, messageTime;
//                TextView messageText, messageUser, messageTime;
//                messageText = (TextView) v.findViewById(R.id.message_text);
//                messageUser = (TextView) v.findViewById(R.id.message_user);
//                messageTime = (TextView) v.findViewById(R.id.message_time);
//
//                messageText.setText(model.getThreadContent());
//                messageUser.setText(model.getUsername());
//                //messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getDateOfCreation()));
//
//            }
//        };
//        listOfMessage.setAdapter(adapter);







    }



    public class ThreadAdapter extends ArrayAdapter<Thread> {
        public ThreadAdapter(Context context, List<Thread> threads) {
            super(context, 0, threads);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Thread user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_thread, parent, false);
            }
            // Lookup view for data population
            TextView messagetext = (TextView) convertView.findViewById(R.id.message_text);
            TextView messageuser = (TextView) convertView.findViewById(R.id.message_user);
            TextView messageTime = (TextView) convertView.findViewById(R.id.message_time);
            TextView messagetitle = (TextView) convertView.findViewById(R.id.message_title);
            // Populate the data into the template view using the data object
            messagetext.setText(user.getThreadContent());
            messageuser.setText(user.getUsername());
            messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", user.getDateOfCreation()));
            messagetitle.setText(user.getTitle());
            // Return the completed view to render on screen
            return convertView;
        }
    }




}


