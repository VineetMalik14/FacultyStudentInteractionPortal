package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CourseStudentList extends AppCompatActivity {

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_student_list);

        final ListView listView = findViewById(R.id.lv_courselist);

        final ArrayList<Courseinfo> courseinfos = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseinfos.clear();
                Log.d("debug","childcount "+ dataSnapshot.getChildrenCount());
                if(dataSnapshot.getChildrenCount()>0)
                for(DataSnapshot course : dataSnapshot.getChildren())
                {
                    if(UserInfo.courses!=null &&UserInfo.courses.contains(course.getKey()))
                    {
                        Courseinfo newcourseinfo = new Courseinfo(course.child("courseID").getValue().toString(),course.child("fullname").getValue().toString(),course.child("professor").getValue().toString());
                        courseinfos.add(newcourseinfo);
                    }
                }

                CourseInfoAdaptor courseInfoAdaptor = new CourseInfoAdaptor(CourseStudentList.this,R.layout.course_info_layout_student,courseinfos);

                listView.setAdapter(courseInfoAdaptor);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}

class Courseinfo{

    String courseid;
    String fullname;
    String profname;

    public Courseinfo(String courseid, String fullname, String profname) {
        this.courseid = courseid;
        this.fullname = fullname;
        this.profname = profname;
    }
}

class CourseInfoAdaptor extends ArrayAdapter<Courseinfo> {

    private static final String TAG = "PollListAdaptor";
    private Context mContext;
    private int mResource;

    public CourseInfoAdaptor(Context context, int resource, List<Courseinfo> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        final String courseid = getItem(position).courseid;
        String fullname = getItem(position).fullname;
        String profname = getItem(position).profname;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView courseidtv = convertView.findViewById(R.id.tv_courseinfo_courseid);
        TextView fullnametv = convertView.findViewById(R.id.tv_courseinfo_coursename);
        TextView proftv = convertView.findViewById(R.id.tv_courseinfo_profname);

        courseidtv.setText(courseid);
        fullnametv.setText("Title: " + fullname);
        proftv.setText("By: " + profname);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseMainPageStudent.courseID = courseid;
                Intent intent = new Intent(getContext(), CourseMainPageStudent.class);
                mContext.startActivity(intent);

            }
        });

        return convertView;
    }


}




