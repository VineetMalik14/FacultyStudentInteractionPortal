package com.iitg.interaction.facultystudentinteractionportal;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class faqactivity extends AppCompatActivity {
  Button faq1;
  Button faq2;
  Button faq3;
    Button faq4;
    Button faq5;

    TextView ans1;
  TextView ans2;
  TextView ans3;
  TextView ans4;
  TextView ans5;

  public  int c1,c2,c3,c4,c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqactivity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FAQ");
        faq1=findViewById(R.id.faq1);
        faq2=findViewById(R.id.faq2);
        faq3=findViewById(R.id.faq3);
        faq4=findViewById(R.id.faq4);
        faq5=findViewById(R.id.faq5);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        ans5=findViewById(R.id.ans5);

        ans1.setVisibility(View.GONE);
        ans2.setVisibility(View.GONE);
        ans3.setVisibility(View.GONE);
        ans4.setVisibility(View.GONE);
        ans5.setVisibility(View.GONE);

        c1=0;
         c2=0;
         c3=0;
         c4=0;c5=0;
         if(UserInfo.usertype.equals("Prof"))
         {
             faq1.setText("Q. How To Add New Course?");
             ans1.setText("login->navbar->cources->Addbutton(at bottom right corner)->filldetails" +
                     "\n   (Your have provide minimum details of course)   ");
         }

          else
         {
             faq1.setText("Q. How To Enroll In new course?");
             ans1.setText("login->navbar->cources->other cources->search cource->Go->Enter enroll Key provided by prof");
         }

        if(UserInfo.usertype.equals("Prof"))
        {
            faq2.setText("Q. How To Add Materials/Events to your course?");
            ans2.setText("login->navbar->cources->Addbutton(at bottom right corner)->filldetails");
        }

        else
        {
            faq2.setText("Q. How To download course material?");
            ans2.setText("login->navbar->cources->other cources->search cource->Go->Enter enroll Key provided by prof");
        }


        if(UserInfo.usertype.equals("Prof"))
        {
            faq3.setText("Q. How To post query in course?");
            ans3.setText("login->navbar->cources->mycourses->discusion forum->click add bottom right corner ");
        }

        else
        {
            faq3.setText("Q. How To post query in course");
            ans3.setText("login->navbar->cources->mycourses->discusion forum->click add bottom right corner ");
        }
        if(UserInfo.usertype.equals("Prof"))
        {
            faq4.setText("Q How to enroll to course?");
            ans4.setText("Prof cannot enroll to a course. ");
        }

        else
        {
            faq4.setText("Q. How to enroll to course?");
            ans4.setText("login->navbar->cources->allcourses->search for your course->click on course-> click on enroll button->enter key ");
        }
        if(UserInfo.usertype.equals("Prof"))
        {
            faq5.setText("Q How to delete a course?");
            ans5.setText("login->click on menu bar on top right -> select the course to delete ");
        }

        else
        {
            faq5.setText("Q.  How to delete a course?");
            ans5.setText("Student cannot delete a course. ");
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
        faq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c4%2==0)
                {
                    ans4.setVisibility(View.VISIBLE);
                    c4++;
                }
                else
                {
                    ans4.setVisibility(View.GONE);
                    c4++;
                }
            }
        });
        faq5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c5%2==0)
                {
                    ans5.setVisibility(View.VISIBLE);
                    c5++;
                }
                else
                {
                    ans5.setVisibility(View.GONE);
                    c5++;
                }
            }
        });




    }
}
