package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText rollnumber;
    EditText department;
    Spinner occupation;
    EditText year;
    EditText password,pass2;
    String usertype;
    Button signupbtn;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataref = firebaseDatabase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        rollnumber = findViewById(R.id.et_rollnumber);
        year = findViewById(R.id.et_year);
        password = findViewById(R.id.et_password);
        pass2 = findViewById(R.id.et_password2);
        occupation = findViewById(R.id.spn_occupation);
        department = findViewById(R.id.et_department);
        signupbtn = findViewById(R.id.btn_signup);


        List<String> list = new ArrayList<String>();
        list.add("Btech");
        list.add("Mtech");
        list.add("PHD");
        list.add("Professor");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupation.setAdapter(dataAdapter);



        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupfunction();
            }
        });

    }

    void signupfunction()
    {
        NewUser newUser;
        String username;

        String occup = occupation.getSelectedItem().toString();


        if(name.getText().toString().isEmpty()||email.getText().toString().isEmpty()||rollnumber.getText().toString().isEmpty()||year.getText().toString().isEmpty()||department.getText().toString().isEmpty()||password.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Enter all details!",Toast.LENGTH_LONG).show();
            return;
        }

        if(!email.getText().toString().endsWith("@iitg.ac.in"))
        {
            Toast.makeText(getApplicationContext(),"Only IITG members",Toast.LENGTH_LONG).show();
            return;
        }

        if(occup=="Professor")
        {
            usertype="Prof";
        }
        else
        {
            usertype="Stud";
        }



        String rep = "";
        username= email.getText().toString().replace("@iitg.ac.in","");

        if(!password.getText().toString().equals(pass2.getText().toString()) )
        {
            Toast.makeText(getApplicationContext(),"Passwords do not match!",Toast.LENGTH_LONG).show();
            return;
        }


        newUser = new NewUser(username,name.getText().toString(),usertype,rollnumber.getText().toString(),email.getText().toString(),occup,department.getText().toString(),year.getText().toString(),password.getText().toString());

        dataref.child(username).setValue(newUser);

        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);


    }

}
