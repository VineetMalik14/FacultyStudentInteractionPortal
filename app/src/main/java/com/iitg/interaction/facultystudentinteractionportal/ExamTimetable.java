package com.iitg.interaction.facultystudentinteractionportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExamTimetable extends AppCompatActivity {

    private String userName;
    private String examType;

    String temp1, temp2, temp3, val, Cname;

    private ArrayList<String> mValArrayList1, mValArrayList2;

    private RadioGroup mRadio;

    private ListView mlistView_Courses;
    private ArrayList<String> mCourseID_ArrayList;
    private ArrayAdapter<String> myArraydapter_Courses;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_timetable);

        userName = UserInfo.username;
        examType = "";

        temp1 = "";
        temp2 = "";
        temp3 = "";
        val = "";
        Cname = "";

        mValArrayList1 = new ArrayList<>();
        mValArrayList2 = new ArrayList<>();

        mRef = FirebaseDatabase.getInstance().getReference();

        mlistView_Courses = (ListView) findViewById(R.id.listView_Courses);
        mCourseID_ArrayList = new ArrayList<>();
        myArraydapter_Courses = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, mCourseID_ArrayList);
        mlistView_Courses.setAdapter(myArraydapter_Courses);

        mRadio = (RadioGroup) findViewById(R.id.radioGroup_Type);
        mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                mCourseID_ArrayList.clear();
                mValArrayList1.clear();
                mValArrayList2.clear();

                View radioButton = mRadio.findViewById(checkedId);
                int index = mRadio.indexOfChild(radioButton);
                RadioButton rb = (RadioButton) findViewById(checkedId);

                temp1 = rb.getText().toString();
                String[] StrArray = temp1.split(" ");
                examType = ""+StrArray[0]+StrArray[1];

                mRef.child("users").child(userName).child("courses").addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {

                        temp1 = dataSnapshot.getValue().toString();
                        mValArrayList1.add(temp1);

                        mValArrayList2.clear();

                        for (int j = 0; j < mValArrayList1.size(); j++) {

                            temp2 = mValArrayList1.get(j);

                            mRef.child("Courses").child(temp2).addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                                    val = "";
                                    String loctemp = dataSnapshot1.child(examType).child("ExamDate").getValue().toString();
                                    String[] StrArray1 = loctemp.split("/");
                                    val += StrArray1[2]+StrArray1[1]+StrArray1[0];
                                    val += "&" + dataSnapshot1.getKey().toString();

                                    if(mValArrayList2.indexOf(val) == -1) {

                                        mValArrayList2.add(val);
                                    }

                                    Collections.sort(mValArrayList2);

                                    mCourseID_ArrayList.clear();

                                    for (int i = 0; i < mValArrayList2.size(); i++) {

                                        temp3 = mValArrayList2.get(i);

                                        String[] StrArr = temp3.split("&");
                                        Cname = StrArr[StrArr.length - 1];

                                        mRef.child("Courses").child(Cname).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                                                String res;

                                                res = "\n";
                                                res += dataSnapshot2.getKey().toString();
                                                res += "\n";
                                                res += "Date: ";
                                                res += dataSnapshot2.child(examType).child("ExamDate").getValue().toString();
                                                res += "\n";
                                                res += "Time: ";
                                                res += dataSnapshot2.child(examType).child("StartTime").getValue().toString();
                                                res += " | Duration: ";
                                                res += dataSnapshot2.child(examType).child("Duration").getValue().toString();
                                                res += "\n";
                                                res += "Venue: "+dataSnapshot2.child(examType).child("Venue").getValue().toString();
                                                res += "\n";
                                                res += dataSnapshot2.child(examType).child("Description").getValue().toString();

                                                if(mCourseID_ArrayList.indexOf(res) == -1) {

                                                    mCourseID_ArrayList.add(res);
                                                    myArraydapter_Courses.notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        //Toast.makeText(MainActivity.this, Integer.toString(mValArrayList1.size()), Toast.LENGTH_SHORT).show();

    }
}






