package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PollLayoutActivity extends AppCompatActivity {
    ListView lv;
    Polls newpoll;
    public int alreadypolledindex = -1;
    PollListAdaptor pollListAdaptor;
    TextView question;
    String currentcourseid;
    int index;
    static Polls clickedpoll;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Courses");

    Integer totalvotes=0;
    Integer optionvotes=0;
    Integer usernum=0;
    Integer optionsusernum=0;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutpoll);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("Polls");

        currentcourseid = CourseMainPageStudent.courseID;
        lv = findViewById(R.id.lv_optionlist);
        question = findViewById(R.id.tv_questionheading);
        final Button editchoicebtn = findViewById(R.id.btn_editpollselection);
        final Button closepollbtn = findViewById(R.id.btn_closepoll);
        final TextView pollstatustv=findViewById(R.id.tv_pollstatus);





        if(!clickedpoll.isactive)
        {
            closepollbtn.setText("Re-pen\nPoll");
            pollstatustv.setText("Poll Closed");
            pollstatustv.setTextColor(Color.RED);
        }
        else
        {
            closepollbtn.setText("Close Poll");
            pollstatustv.setText("Poll Opened");
            pollstatustv.setTextColor(Color.rgb(34,139,34));
        }


        if(clickedpoll.creater.equals(UserInfo.username)||UserInfo.usertype.equals("Prof"))
        {
            closepollbtn.setVisibility(View.VISIBLE);
        }
        else {
            closepollbtn.setVisibility(View.INVISIBLE);
        }


        final Intent intent = getIntent();
        //index = intent.getIntExtra("index", -1);


        question.setText(clickedpoll.question);
        ArrayList<Options> optionlist = clickedpoll.options;
//        optionlist.add(new Options("This is option one"));
//        optionlist.add(new Options("This is option two"));
//        optionlist.add(new Options("This is option three"));
//        optionlist.add(new Options("This is option four"));
//        optionlist.add(new Options("This is option five"));


        pollListAdaptor = new PollListAdaptor(PollLayoutActivity.this, R.layout.layout_polloptions, clickedpoll.options);

        if(clickedpoll.users!=null && clickedpoll.users.contains(UserInfo.username))
        {
            for(Options op : clickedpoll.options)
            {
                if(op.userslist!=null && op.userslist.contains(UserInfo.username))
                {
                    alreadypolledindex = clickedpoll.options.indexOf(op);
                    break;
                }
            }
        }
        lv.setAdapter(pollListAdaptor);

            if(alreadypolledindex==-1)
            {
                editchoicebtn.setVisibility(View.INVISIBLE);
            }
            else {
                editchoicebtn.setVisibility(View.VISIBLE);
            }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //================================================================================================================


                dataref.child(currentcourseid).child("Polls").child(clickedpoll.uniqueid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
/*                        optionvotes = dataSnapshot.child("options").child(String.valueOf(position)).child("votes").getValue(Integer.class);
                        optionsusernum=dataSnapshot.child("options").child(String.valueOf(position)).child("usernum").getValue(Integer.class);
                        usernum = dataSnapshot.child("usernum").getValue(Integer.class);
                        optionvotes++;
                        optionsusernum++;
                        usernum++;*/
                        clickedpoll = dataSnapshot.getValue(Polls.class);

                        if(clickedpoll.isactive)
                        {
                            if (!clickedpoll.addvote(position, UserInfo.username)) {
                                Toast.makeText(getApplicationContext(), "You have already Polled for option " + String.valueOf(alreadypolledindex+1), Toast.LENGTH_LONG).show();
                            } else {
                                dataref.child(currentcourseid).child("Polls").child(clickedpoll.uniqueid).setValue(clickedpoll);
                                pollListAdaptor.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Successfully Polled for " + clickedpoll.options.get(position).optiontext, Toast.LENGTH_LONG).show();
                                editchoicebtn.setVisibility(View.VISIBLE);
                                alreadypolledindex = position;
                                startActivity(intent);
                                finish();

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Poll is Closed, You can't Choose any option",Toast.LENGTH_LONG).show();
                        }






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//===========================================================================================================================


            }
        });



            editchoicebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickedpoll.isactive)
                    {
                        clickedpoll.removeuser(UserInfo.username);
                        dataref.child(currentcourseid).child("Polls").child(clickedpoll.uniqueid).setValue(clickedpoll);
                        Toast.makeText(getApplicationContext(),"Your choice is removed, You can select a new option.",Toast.LENGTH_LONG).show();
                        pollListAdaptor.notifyDataSetChanged();
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Poll is Closed. You can't change your choice now.",Toast.LENGTH_LONG).show();
                    }

                }
            });

            closepollbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(clickedpoll.creater.equals(UserInfo.username) || UserInfo.usertype.equals("Prof"))
                    {
                        if(clickedpoll.isactive)
                        {

                            dataref.child(currentcourseid).child("Polls").child(clickedpoll.uniqueid).child("isactive").setValue(false);
                            //pollListAdaptor.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"You have Closed the Poll.",Toast.LENGTH_LONG).show();
                        }
                        else {

                            dataref.child(currentcourseid).child("Polls").child(clickedpoll.uniqueid).child("isactive").setValue(true);
                            //pollListAdaptor.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"You have Opened the Poll again.",Toast.LENGTH_LONG).show();
                        }


                        if(!clickedpoll.isactive)
                        {
                            closepollbtn.setText("Re-pen\nPoll");
                            pollstatustv.setText("Poll Closed");
                            pollstatustv.setTextColor(Color.RED);
                        }
                        else
                        {
                            closepollbtn.setText("Close Poll");
                            pollstatustv.setText("Poll Opened");
                            pollstatustv.setTextColor(Color.rgb(34,139,34));
                        }


                    }

                }
            });




        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(currentcourseid).child("Polls").child(clickedpoll.uniqueid);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clickedpoll = dataSnapshot.getValue(Polls.class);
                pollListAdaptor.notifyDataSetChanged();

                if(!clickedpoll.isactive)
                {
                    closepollbtn.setText("Re-pen\nPoll");
                    pollstatustv.setText("Poll Closed");
                    pollstatustv.setTextColor(Color.RED);
                }
                else
                {
                    closepollbtn.setText("Close Poll");
                    pollstatustv.setText("Poll Opened");
                    pollstatustv.setTextColor(Color.rgb(34,139,34));
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // SWIPE TO REFRESH..

        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(currentcourseid).child("Polls").child(clickedpoll.uniqueid);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        clickedpoll= dataSnapshot.getValue(Polls.class);
                        pollListAdaptor.notifyDataSetChanged();

                        pullToRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });






    }
        public View getViewByPosition ( int pos, ListView listView){
            final int firstListItemPosition = listView.getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

            if (pos < firstListItemPosition || pos > lastListItemPosition) {
                return listView.getAdapter().getView(pos, null, listView);
            } else {
                final int childIndex = pos - firstListItemPosition;
                return listView.getChildAt(childIndex);
            }
        }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }

}
