package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;

public class PollLayoutActivity extends AppCompatActivity {
    ListView lv;
    int alreadypolledindex=-1;

    TextView question;
    String currentcourseid;
    int index;
    static Polls clickedpoll;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Courses");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutpoll);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Polls");

        currentcourseid = CourseMainPageStudent.courseID;
        lv = findViewById(R.id.lv_optionlist);
        question = findViewById(R.id.tv_questionheading);

        final Intent intent = getIntent();
        index = intent.getIntExtra("index",-1);

        question.setText(clickedpoll.question);
        ArrayList<Options> optionlist = clickedpoll.options;
//        optionlist.add(new Options("This is option one"));
//        optionlist.add(new Options("This is option two"));
//        optionlist.add(new Options("This is option three"));
//        optionlist.add(new Options("This is option four"));
//        optionlist.add(new Options("This is option five"));





        final PollListAdaptor pollListAdaptor = new PollListAdaptor(PollLayoutActivity.this,R.layout.layout_polloptions,optionlist);

        lv.setAdapter(pollListAdaptor);
        if(clickedpoll.users!=null)
        for(int i =0 ;i<clickedpoll.users.size();i++)
        {
            if(clickedpoll.users.get(i).username.equals(UserInfo.username))
            {
                alreadypolledindex=clickedpoll.users.get(i).index;
                //lv.getChildAt(i - lv.getFirstVisiblePosition()).findViewById(R.id.tv_option).setBackgroundColor(Color.parseColor("#008ecc"));
                RelativeLayout cardView = (RelativeLayout) getViewByPosition(clickedpoll.users.get(i).index,lv).findViewById(R.id.rl_polloption);
                cardView.setBackground(getResources().getDrawable(R.color.colorPrimary));
                cardView.setBackgroundColor(0xFFEE3333);
//                cardView.setVisibility(View.INVISIBLE);
//                RelativeLayout rl = (RelativeLayout)lv.getItemAtPosition(i);
//                rl.setBackgroundColor(Color.parseColor("#008ecc"));

                Log.d("debug", "i am here inside ! " + getViewByPosition(i,lv).findViewById(R.id.tv_option));

                break;
            }
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
//                builder1.setMessage("Are you sure ?");
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent1 = getIntent();
//                                startActivity(intent);
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();

                if(!clickedpoll.addvote(position,UserInfo.username)){
                    Toast.makeText(getApplicationContext(),"You have already Polled for option "+String.valueOf(alreadypolledindex),Toast.LENGTH_LONG).show();
                }
                else
                {
                    dataref.child(currentcourseid).child("Polls").child(String.valueOf(index)).setValue(clickedpoll);
                    pollListAdaptor.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Successfully Polled for "+clickedpoll.options.get(position).optiontext,Toast.LENGTH_LONG).show();
                }
//                Intent intent1 = getIntent();
//                startActivity(intent1);

            }
        });

//        final GenericTypeIndicator<ArrayList<Polls>> t = new GenericTypeIndicator<ArrayList<Polls>>() {};
//
//        dataref.child(currentcourseid).child("Polls").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<Polls> pollslist = dataSnapshot.getValue(t);
//                Log.d("debug","polls changed on database !");
//                if(pollslist!=null)
//                {
//                    pollslist.set(index,clickedpoll);
//                    dataref.child(currentcourseid).child("Polls").setValue(pollslist);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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
