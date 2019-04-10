package com.iitg.interaction.facultystudentinteractionportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView fullname;
    TextView username;
    TextView email;
    TextView occupation;
    TextView rollnumber;
    TextView year;
    TextView department;
    ListView ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullname = findViewById(R.id.tv_name);
        username = findViewById(R.id.tv_username);
        email = findViewById(R.id.tv_email);
        occupation=findViewById(R.id.tv_occupation);
        rollnumber=findViewById(R.id.tv_rollnumber);
        year = findViewById(R.id.tv_year);
        department = findViewById(R.id.tv_department);


        if(UserInfo.logined)
        {
            fullname.setText(UserInfo.fullname);
            username.setText("username: "+UserInfo.username);
            email.setText("Email: "+UserInfo.email);
            occupation.setText("Occupation: "+UserInfo.occupation);
            department.setText("Department: "+UserInfo.department);

            if(UserInfo.usertype.equals("Stud"))
            {
                rollnumber.setText("Roll Number: "+UserInfo.rollnumber);
                year.setText("Year: "+UserInfo.year);
            }
            else
            {
                rollnumber.setVisibility(View.INVISIBLE);
                year.setVisibility(View.INVISIBLE);
            }
        }
          ll = (ListView) findViewById(R.id.lv_courses);
        TextView emp = findViewById(R.id.tv_emptylist);
        if(!(UserInfo.courses==null)) {

            final ArrayAdapter<String> adpater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,UserInfo.courses);
            ll.setAdapter(adpater);
        }

        else
            {
                emp.setVisibility(View.VISIBLE);
            }

      ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /* open Cources Form */
          }
      });

    }
}
