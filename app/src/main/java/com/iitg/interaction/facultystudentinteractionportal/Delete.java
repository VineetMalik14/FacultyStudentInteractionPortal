package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Delete extends AppCompatActivity implements Sure_dialog.SureDialogListener  {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    String currentuser = UserInfo.username;
    String course;
    ArrayList<String> al = new ArrayList<>();
    ArrayAdapter<String> ad;

    ListView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


        r = (ListView) findViewById(R.id.courses);
        al.clear();
        ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        r.setAdapter(ad);
        ad.notifyDataSetChanged();
        DatabaseReference us = db.getReference().child("users").child(currentuser).child("courses");
        us.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value  = dataSnapshot.getValue(String.class);
                al.add(value);
                ad.notifyDataSetChanged();


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

        r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                course = al.get(position);
                String pass = course;
                //Toast.makeText(getApplicationContext(), course,Toast.LENGTH_SHORT).show();
                //doit(pass, ad);
                openDialog();



            }
        });

    }

    public void openDialog(){
        Sure_dialog sd = new Sure_dialog();
        sd.show(getSupportFragmentManager(), "Sure Dialogue");
    }
    public void doit(String coursec, final ArrayAdapter<String> ad){
        DatabaseReference pk = db.getReference().child("Courses");
        pk.child(coursec).removeValue();
        pk = db.getReference().child("users");
        pk.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NewUser uservalue = dataSnapshot.getValue(NewUser.class);
                String username = uservalue.username;
                DatabaseReference inside = db.getReference().child("users").child(username).child("courses");
                delete(inside, username, ad);
                Intent i = new Intent(Delete.this , MainActivity.class );
//                startActivity(i);
                finish();
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

    public void delete(final DatabaseReference inside, String user, final ArrayAdapter<String> ad1){

        final GenericTypeIndicator<ArrayList<String>> t= new GenericTypeIndicator<ArrayList<String>>(){};
        inside.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> ar ;
                ar = dataSnapshot.getValue(t);
                if(ar!=null)
                {
                    ar.remove(course);
                    inside.setValue(ar);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(), course + " removed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void applyt(String ans) {
        Toast.makeText(getApplicationContext(), course,Toast.LENGTH_SHORT).show();
        doit(course, ad);
    }
}
