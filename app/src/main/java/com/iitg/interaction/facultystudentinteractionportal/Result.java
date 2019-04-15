package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Result extends AppCompatActivity implements EnrollDialog.EnrollDialogListener{

    TextView name;
    TextView desc;
    private String originalkey;
    String currentuser = "barney";
    private ArrayList<String> al;
    int check = 0;
    private String gotkey;
    long count =0;
    int temp = 0;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        Intent i = getIntent();
        name = findViewById(R.id.name);
        name.setText(i.getStringExtra(Search.name));
        DatabaseReference us = db.getReference().child("Courses").child(name.getText().toString()).child("description");
        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                desc = findViewById(R.id.descripton);
                desc.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button b =(Button) findViewById(R.id.enroll);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference in = db.getReference().child("users").child("kancha").child("courses");
                in.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if(dataSnapshot.getValue(String.class).contains(name.getText().toString())){
                            check = 1;
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
                if(check == 0){

                    openDialog();

                }else{
                    Toast.makeText(getApplicationContext(),"You are already enrolled in this course.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openDialog(){

        EnrollDialog ed = new EnrollDialog();
        ed.show(getSupportFragmentManager(), "Enroll Dialog");

    }

    @Override
    public void applyTexts(String key) {
        gotkey= key;
        DatabaseReference pk = db.getReference().child("Courses").child(name.getText().toString()).child("courseKey");
        pk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                originalkey = dataSnapshot.getValue(String.class);
                //name.setText(originalkey + " - " + gotkey );
                if(originalkey.equals(gotkey)){


                    //;
                    DatabaseReference am = db.getReference().child("users").child(currentuser);
                    //al.clear();
                    //al.add(name.getText().toString());
                    /*final GenericTypeIndicator<ArrayList<String>> t = new  GenericTypeIndicator<ArrayList<String>>(){};
                    am.child("courses").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            al = dataSnapshot.getValue(t);
                            al.add(name.getText().toString());
                            am.child("courses").setValue(al);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/
                    am.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                            /*String download = dataSnapshot.getValue(String.class);
                            al.add(download);*/

                            if(dataSnapshot.getKey().equals("courses")){
                                count = dataSnapshot.getChildrenCount();
                            }



                            if(temp == 0){
                                temp = 1;
                                //name.setText(name.getText().toString() + " - " + count);
                                DatabaseReference jkl = db.getReference().child("users").child(currentuser).child("courses");
                                jkl.child(Long.toString(count)).setValue(name.getText().toString());
                            }


                            //name.setText(name.getText().toString() + " - " + count );






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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //name.setText(originalkey);
        /*if(originalkey.equals(key)){
            DatabaseReference am = db.getReference().child(currentuser).child("courses");
            am.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    count = dataSnapshot.getChildrenCount();
                    String aj = "" + count;
                    name.setText(aj);
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
        }*/
    }



}
