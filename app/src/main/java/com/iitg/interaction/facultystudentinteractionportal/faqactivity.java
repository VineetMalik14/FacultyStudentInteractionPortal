package com.iitg.interaction.facultystudentinteractionportal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class faqactivity extends AppCompatActivity {
  Button faq1;
  Button faq2;
  Button faq3;
  TextView ans1;
  TextView ans2;
  TextView ans3;
  public  int c1,c2,c3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);
        faq1=findViewById(R.id.faq1);
        faq2=findViewById(R.id.faq2);
        faq3=findViewById(R.id.faq3);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);


        ans1.setVisibility(View.GONE);
        ans2.setVisibility(View.GONE);
        ans3.setVisibility(View.GONE);
        c1=0;
         c2=0;
         c3=0;
         if(UserInfo.usertype.equals("Prof"))
         {
             faq1.setText("How To Add New Course");
             ans1.setText("login->navbar->cources->Addbutton(at bottom right corner)->filldetails");
         }

          else
         {
             faq1.setText("How To Enroll In new course");
             ans1.setText("login->navbar->cources->other cources->search cource->Go->Enter enroll Key provided by prof");
         }

        if(UserInfo.usertype.equals("Prof"))
        {
            faq2.setText("How To Add Materials/Events to your course");
            ans2.setText("login->navbar->cources->Addbutton(at bottom right corner)->filldetails");
        }

        else
        {
            faq2.setText("How To download course material");
            ans2.setText("login->navbar->cources->other cources->search cource->Go->Enter enroll Key provided by prof");
        }

        faq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c1%2==0)
                {
                    ans1.setVisibility(View.VISIBLE);
                    c1++;
                }
                else
                {
                    ans1.setVisibility(View.GONE);
                    c1++;
                }
            }
        });
        faq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c2%2==0)
                {
                    ans2.setVisibility(View.VISIBLE);
                    c2++;
                }
                else
                {
                    ans2.setVisibility(View.GONE);
                    c2++;
                }
            }
        });
        faq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c3%2==0)
                {
                    ans3.setVisibility(View.VISIBLE);
                    c3++;
                }
                else
                {
                    ans3.setVisibility(View.GONE);
                    c3++;
                }
            }
        });




    }
}
