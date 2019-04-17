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
    private PollListAdaptor pollListAdaptor;
    TextView question;
    String currentcourseid;
    int index;
    static Polls clickedpoll;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Courses");

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
        index = intent.getIntExtra("index", -1);


        question.setText(clickedpoll.question);
        ArrayList<Options> optionlist = clickedpoll.options;
//        optionlist.add(new Options("This is option one"));
//        optionlist.add(new Options("This is option two"));
//        optionlist.add(new Options("This is option three"));
//        optionlist.add(new Options("This is option four"));
//        optionlist.add(new Options("This is option five"));


        pollListAdaptor = new PollListAdaptor(PollLayoutActivity.this, R.layout.layout_polloptions, optionlist);

        lv.setAdapter(pollListAdaptor);
        if (clickedpoll.users != null)
            for (int i = 0; i < clickedpoll.users.size(); i++) {
                if (clickedpoll.users.get(i).username.equals(UserInfo.username)) {
                    alreadypolledindex = clickedpoll.users.get(i).index;
                    //lv.getChildAt(i - lv.getFirstVisiblePosition()).findViewById(R.id.tv_option).setBackgroundColor(Color.parseColor("#008ecc"));
                    RelativeLayout cardView = (RelativeLayout) getViewByPosition(clickedpoll.users.get(i).index, lv).findViewById(R.id.rl_polloption);
                    cardView.setBackground(getResources().getDrawable(R.color.colorPrimary));
                    cardView.setBackgroundColor(0x55008ecc);

                    Log.d("debug", "i am here inside ! " + alreadypolledindex);

                    break;
                }
            }

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


                if(clickedpoll.isactive)
                {
                    if (!clickedpoll.addvote(position, UserInfo.username)) {
                        Toast.makeText(getApplicationContext(), "You have already Polled for option " + String.valueOf(alreadypolledindex+1), Toast.LENGTH_LONG).show();
                    } else {
                        dataref.child(currentcourseid).child("Polls").child(String.valueOf(index)).setValue(clickedpoll);
                        pollListAdaptor.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Successfully Polled for " + clickedpoll.options.get(position).optiontext, Toast.LENGTH_LONG).show();
                        editchoicebtn.setVisibility(View.VISIBLE);
                        alreadypolledindex = position;
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Poll is Closed, You can't Choose any option",Toast.LENGTH_LONG).show();
                }

//===========================================================================================================================


            }
        });



            editchoicebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickedpoll.isactive)
                    {
                        for(userlist user : clickedpoll.users)
                        {
                            if(user.username.equals(UserInfo.username))
                            {
                                clickedpoll.removeuser(UserInfo.username);

                                dataref.child(currentcourseid).child("Polls").child(String.valueOf(index)).setValue(clickedpoll);
                                pollListAdaptor.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),"Your choice is removed, now you can select a different option.",Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
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
                            clickedpoll.closePoll();
                            dataref.child(currentcourseid).child("Polls").child(String.valueOf(index)).setValue(clickedpoll);
                            pollListAdaptor.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"You have Closed the Poll.",Toast.LENGTH_LONG).show();
                        }
                        else {
                            clickedpoll.openPoll();
                            dataref.child(currentcourseid).child("Polls").child(String.valueOf(index)).setValue(clickedpoll);
                            pollListAdaptor.notifyDataSetChanged();
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



/*
        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Courses").child(currentcourseid).child("Polls").child(String.valueOf(index));


        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clickedpoll = dataSnapshot.getValue(Polls.class);
                Log.d("debug","I am inside polllayout datachange ");
                if(clickedpoll!=null)
                {
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
               pollListAdaptor.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/




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
