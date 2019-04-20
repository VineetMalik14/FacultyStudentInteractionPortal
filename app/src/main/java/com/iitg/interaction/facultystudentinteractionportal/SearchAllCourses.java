package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchAllCourses extends Fragment {



    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    public  static String name = "com.iitg.interaction.facultystudentinteractionportal.name";
    ArrayList<String> al = new ArrayList<String>();
    String currentuser = "barney";
    String item;

    ListView r;
    TextView t;
    Activity truth = getActivity();
    Intent intent;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_all_courses);
        GoClick(view);


        Button go = (Button) getView().findViewById(R.id.Go);
        t = (TextView) getView().findViewById(R.id.srch);

        r = (ListView) getView().findViewById(R.id.table);

        r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                item = al.get(position);
                if(UserInfo.courses==null)
                {
                    UserInfo.courses = new ArrayList<>();
                }
                if(UserInfo.courses.contains(item))
                {
                    intent = new Intent(getActivity(),CourseMainPageStudent.class);
                    CourseMainPageStudent.courseID=item;

                    DatabaseReference us = db.getReference().child("Courses").child(item).child("description");
                    us.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);


                            intent.putExtra("CourseDescription", value);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {



                        }
                    });


                    DatabaseReference po = db.getReference().child("Courses").child(item).child("marksDistribution");
                    po.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String  marks = dataSnapshot.getValue(String.class);
                            intent.putExtra("CourseMarks", marks);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {



                        }
                    });

                    us = db.getReference().child("Courses").child(item).child("fullname");
                    us.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            intent.putExtra("CourseTitle", value);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    us = db.getReference().child("Courses").child(item).child("syllabus");
                    us.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            intent.putExtra("CourseSyllabus", value);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    us = db.getReference().child("Courses").child(item).child("timeSlots");
                    us.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            intent.putExtra("CourseTimeSlots", value);
                            Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

                            intent.putExtra("CourseID",item);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else {

                    Intent i = new Intent(getActivity() , StudentEnrollActivity.class);


                    i.putExtra("name", item);

                    startActivity(i);
                    getActivity().finish();

                }

                //String prev= t.getText().toString();
                //t.setText(prev + item );
//                DatabaseReference dm = db.getReference().child("users").child(currentuser).child("courses");
//                dm.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        String val = dataSnapshot.getValue(String.class);
//                        //t.setText(t.getText() + " - " + val);
//                        int check = 0;
//                        if(val.equals(item)){
//
//                            check = 1;
//                        }else{
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                /*Intent i = new Intent(getActivity() , StudentEnrollActivity.class );
//                i.putExtra("name", item);
//
//                startActivity(i);*/
//                /*if(){
//
//                }else{
//                    Intent i = new Intent(getActivity() , StudentEnrollActivity.class );
//                    i.putExtra("name", item);
//
//                    startActivity(i);
//                }*/
//



            }
        });



        go.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                GoClick(view);
            }
        });

        //ad.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_search_all_courses, container, false);
        return rootView;
    }



    public void GoClick (View view){



        r = (ListView) getView().findViewById(R.id.table);
        al.clear();
        al = new ArrayList<String>();
        final ArrayAdapter<String>  ad = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, al);
        r.setAdapter(ad);
        ad.notifyDataSetChanged();

        DatabaseReference us = db.getReference().child("Courses");

        us.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**/

                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    String dat =data.getKey();
                    String prev = t.getText().toString();
                    if(dat.toLowerCase().contains(prev.toLowerCase())){

                        al.add(dat);
                    }

                }
                ad.notifyDataSetChanged();


                    /*String base = dataSnapshot.getValue().toString();
                    String[] split =  base.split(",");*/
                    /*int k = 0;
                    for(k = 0; k< split.length; k++){
                        if(split[k].contains("courseID")){
                            String ans = split[k].substring(10);
                            String prev = t.getText().toString();
                            if(ans.toLowerCase().contains(prev.toLowerCase())){

                                al.add(ans);
                            }
                            *//*String prev = t.getText().toString();
                            t.setText(prev + ans);*//*


                        }
                    }*/

//                hideKeyboard(truth);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    public static void hideKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }



}
