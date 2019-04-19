package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CourseDiscussionFragmentStudent extends Fragment {

    private DatabaseReference databaseReference;
    View dialogView;
    private DatabaseReference databaseReference2;
    String course;
    List<String> ids;
    List<Thread> threads;
    String username;
    String usertype;
    ThreadAdapter adapter;
    ListView listView;


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//        setTitle("Discussion Forum CS204" );

        super.onCreate(savedInstanceState);
        username = UserInfo.username;
        usertype = UserInfo.usertype;
//        course = CourseMainPageStudent.courseID;
        course = "CS101";
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child("CS101").child("threads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((getActivity() != null) && (getView() != null)){

                    threads = new ArrayList<Thread>();
                    ids = new ArrayList<String>();
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        ids.add(messageSnapshot.getKey());
                        Thread thread = messageSnapshot.getValue(Thread.class);
                        threads.add(thread);
                        //  Log.v("Title", event.getTitle());
                    }
                    Collections.reverse(threads);
                    Collections.reverse(ids);



                    adapter = new ThreadAdapter(getActivity(), threads);

                    listView = (ListView) getView().findViewById(R.id.lv_thread);
                    listView.setAdapter(adapter);




                    if(UserInfo.usertype.equals("Prof")){

                        registerForContextMenu(listView);

                    }




                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // now we have  all the value that will be needed for
                            Thread item = adapter.getItem(position);
                            String key = ids.get(position);
                            // now send the key with the intent you are showing
                            Intent intent = new Intent(getActivity(), ThreadReplies.class);
                            intent.putExtra("KEY", key);
                            intent.putExtra("COURSE", course);
                            intent.putExtra("TITLE", item.getTitle());
                            intent.putExtra("CONTENT", item.getThreadContent());
                            intent.putExtra("USER", item.getUsername());
                            intent.putExtra("TIME", DateFormat.format("dd-MM-yyyy (HH:mm:ss)", item.getLastModified()) );
                            intent.putExtra("COURSECLOSED", item.isThreadClosed());


                            startActivity(intent);




                            // now we have the item in the
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        FloatingActionButton newthread =  getView().findViewById(R.id.new_thread);

        newthread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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

                        // ArrayList<Replies> repliesArrayList = new ArrayList<Replies>();
                        if (title.getText().toString().equals("") || content.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Please enter correct Title and Content", Toast.LENGTH_SHORT).show();
                        } else {
                            Thread newthread = new Thread(false, username, usertype, content.getText().toString(), title.getText().toString(), c, c);
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

        ListView listOfMessage = (ListView)getView().findViewById(R.id.lv_thread);




    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lv_thread) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
            String[] menuItems = {"Delete Thread", "Close Thread"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(100, i, i, menuItems[i]);

            }
        }


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
//        if (getUserVisibleHint()) {
            if (item.getGroupId() == 100) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                long menuItemIndex = item.getItemId();
                String threadid = ids.get(info.position);
                databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Courses").child(course).child("threads").child(threadid);

                if (menuItemIndex == 0) { // This is to delete the thread
                    Log.d("Context menu", "Not reaching here");
                    databaseReference2.removeValue();
                    // succesfull
                    return true;
                }
                if (menuItemIndex == 1) {      // this is to make the thread closed

                    databaseReference2.child("ThreadClosed").setValue(true);
                    return true;
                }

                return true;
            }

            return true;
//        }
//        else
//            return false;
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





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_discussion_threads, container, false);
        return rootView;
    }
}