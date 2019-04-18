package com.iitg.interaction.facultystudentinteractionportal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CourseStudentList extends Fragment {

    private DatabaseReference databaseReference;
    // TODO see this
    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_course_student_list);

        if(UserInfo.usertype.equals("Stud"))
        {
            FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_add_course);
            floatingActionButton.setVisibility(View.GONE);
        }
        final ListView listView = getView().findViewById(R.id.lv_courselist);

        final ArrayList<Courseinfo> courseinfos = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (getActivity() != null){
                    courseinfos.clear();
                    for(DataSnapshot course : dataSnapshot.getChildren())
                    {
                        if(UserInfo.courses!=null &&UserInfo.courses.contains(course.getKey()))
                        {
                            Courseinfo newcourseinfo = new Courseinfo(course.child("courseID").getValue().toString(),course.child("fullname").getValue().toString(),course.child("professor").getValue().toString());
                            courseinfos.add(newcourseinfo);
                        }
                    }

                    CourseInfoAdaptor courseInfoAdaptor = new CourseInfoAdaptor(getActivity(),R.layout.course_info_layout_student,courseinfos);

                    listView.setAdapter(courseInfoAdaptor);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_add_course);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CourseAdd.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_course_student_list, container, false);
        return rootView;
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
        final String[] CourseTitle = new String[1];
        final String[] CourseDescription = new String[1];
        final String[] CourseSyllabus = new String[1];
        final String[] CourseMarks = new String[1];
        final String[] CourseTimeSlots = new String[1];
//        final String[] CourseDateOfCreation = new String[1];

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


                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Courses").child(courseid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(getContext(), CourseMainPageStudent.class);
                        CourseTitle[0] = dataSnapshot.child("fullname").getValue().toString();
                        CourseDescription[0] = dataSnapshot.child("description").getValue().toString();
                        CourseSyllabus[0] = dataSnapshot.child("syllabus").getValue().toString();
                        CourseMarks[0] = dataSnapshot.child("marksDistribution").getValue().toString();
                        CourseTimeSlots[0] = dataSnapshot.child("timeSlots").getValue().toString();
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date CourseDateOfCreation =dataSnapshot.child("dateOfCreation").getValue(Date.class);
//                        formatter.format(CourseDateOfCreation);
                        intent.putExtra("CourseID",courseid);
                        intent.putExtra("CourseTitle",CourseTitle[0]);
                        intent.putExtra("CourseDescription",CourseDescription[0]);
                        intent.putExtra("CourseSyllabus",CourseSyllabus[0]);
                        intent.putExtra("CourseMarks",CourseMarks[0]);
                        intent.putExtra("CourseTimeSlots",CourseTimeSlots[0]);
                        intent.putExtra("CourseDateOfCreation",CourseDateOfCreation.toString());
                        Log.d("ds","   edjk" + CourseDateOfCreation);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        return convertView;
    }


}