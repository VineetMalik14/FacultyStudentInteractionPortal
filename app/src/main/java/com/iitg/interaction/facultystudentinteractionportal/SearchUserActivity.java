package com.iitg.interaction.facultystudentinteractionportal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.common.util.Base64Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    Button searchbtn;
    EditText searchinput;
    ListView userlv;
    String input;
    String attribute;
    ArrayList<NewUser> userArrayList;
    RadioGroup radioGroup;
    boolean isfullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        searchbtn = findViewById(R.id.btn_searchuser_search);
        searchinput = findViewById(R.id.et_searchuser_username);
        userlv = findViewById(R.id.lv_searchuser_userlist);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userArrayList.clear();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren())
                        {
                            if(data.child(attribute).getValue(String.class).contains(attribute))
                            {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });





    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radiobtn_username:
                if (checked)
                    isfullname=false;
                attribute="username";
                    // Pirates are the best
                    break;
            case R.id.radiobtn_fullname:
                if (checked)
                    isfullname=true;
                attribute="fullname";
                    // Ninjas rule
                    break;
        }
    }
}
