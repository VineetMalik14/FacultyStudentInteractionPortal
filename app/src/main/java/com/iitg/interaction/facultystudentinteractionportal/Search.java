package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Search extends AppCompatActivity {



    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    public  static String name = "com.iitg.interaction.facultystudentinteractionportal.name";
    ArrayList<String> al = new ArrayList<String>();
    ArrayList<String> details = new ArrayList<String>();

    ListView r;
    TextView t;
    Activity truth = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


        Button go = (Button) findViewById(R.id.Go);
        t = (TextView) findViewById(R.id.srch);

        r = (ListView) findViewById(R.id.table);

        r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = al.get(position);
                //String prev= t.getText().toString();
               //t.setText(prev + item );
                Intent i = new Intent(Search.this , Result.class );
                i.putExtra(name, item);



                startActivity(i);


            }
        });


        /*go.setOnClickListener(new View.OnClickListener() {



        });*/

        //ad.notifyDataSetChanged();
    }



    public void GoClick (View view){



            r = (ListView) findViewById(R.id.table);
            al.clear();
            details.clear();
            al = new ArrayList<String>();
            details = new ArrayList<String>();
            final ArrayAdapter<String>  ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
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
                        String fullname = data.child("fullname").getValue().toString();
                        if(dat.toLowerCase().contains(prev.toLowerCase())){

                            al.add(dat);

                            /*t.setText(t.getText() + " - " + fullname);*/
                            details.add(dat +  " - " + fullname);

                        }else if (fullname.toLowerCase().contains(prev.toLowerCase())){

                            al.add(dat);

                            /*t.setText(t.getText() + " - " + fullname);*/
                            details.add(dat +  " - " + fullname);
                            
                        }


                    }
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

                    hideKeyboard(truth);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        ad.notifyDataSetChanged();




    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
