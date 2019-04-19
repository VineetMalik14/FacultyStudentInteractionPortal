package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class EventsMainPage extends Fragment {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    ArrayList<String> list_today = new ArrayList<String>();
    ArrayList<String> list_upcoming = new ArrayList<String>();
    ListView today;
    String formattedDate;
    public View dialogView;
    public View dialogView2;
    Context ye;
    List<HashMap<String, String>> listItems;
    List<HashMap<String, String>> listdo;
    String currentuser  = UserInfo.username;
    ArrayList<Object> ObjectList1;
    ArrayList<Object> ObjectList2;
    HashMap<String, String> nameAddresses = new HashMap<>();
    HashMap<String, String > namedo = new HashMap<>();
    ListView upcoming;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_events_main_page, container, false);
        setHasOptionsMenu(true);

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_main_page);*/

        today = Objects.requireNonNull(getView()).findViewById(R.id.table_today);
        upcoming = Objects.requireNonNull(getView()).findViewById(R.id.table_upcoming);

        list_today.clear();
        list_upcoming.clear();
        ye = this.getActivity();

        /*final ArrayAdapter<String>  ad_today = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_today);
        final ArrayAdapter<String>  ad_upcoming = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_upcoming);*/

        ObjectList1 = new ArrayList<>();
        ObjectList2 = new ArrayList<>();
        nameAddresses = new HashMap<>();
        namedo = new HashMap<>();
        /*nameAddresses.put("Diana", "3214 Broadway Avenue");
        nameAddresses.put("Tyga", "343 Rack City Drive");
        nameAddresses.put("Rich Homie Quan", "111 Everything Gold Way");
        nameAddresses.put("Donna", "789 Escort St");
        nameAddresses.put("Bartholomew", "332 Dunkin St");
        nameAddresses.put("Eden", "421 Angelic Blvd");*/

        listItems = new ArrayList<>();
        listdo = new ArrayList<>();

        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(d);
        //Toast.makeText(getApplicationContext(),formattedDate,Toast.LENGTH_SHORT).show();

        DatabaseReference getthem = db.getReference().child("users").child(currentuser).child("courses");
        getthem.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                DatabaseReference getevents = db.getReference().child("Courses").child(value).child("Events");

                getevents.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot data : dataSnapshot.getChildren())
                        {

                            Event event = data.getValue(Event.class);
                            String ed = event.getDateOfEvent();
                            String title = event.getTitle();
                            String time = event.getTimeOfEvent();

                            //Toast.makeText(getApplicationContext(),event + " - " + time,Toast.LENGTH_SHORT).show();
                            if(event!=null){
                                if(ed.equals(formattedDate)){
                                    //Toast.makeText(getApplicationContext(),"equal chal rha h",Toast.LENGTH_SHORT).show();
                                    nameAddresses.put(title, time);
                                    ObjectList1.add(event);
                                }else{
                                    namedo.put(title, ed);
                                    ObjectList2.add(event);
                                }
                            }





                        }

                        //final ArrayAdapter<String>  ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameAddresses);

                        SimpleAdapter ad_today = new SimpleAdapter(ye, listItems, R.layout.list_item_events,
                                new String[]{"First Line", "Second Line"},
                                new int[]{R.id.text1, R.id.text2});

                        SimpleAdapter ad_upcoming = new SimpleAdapter(ye, listdo, R.layout.list_item_events,
                                new String[]{"First Line", "Second Line"},
                                new int[]{R.id.text1, R.id.text2});


                        Iterator it = nameAddresses.entrySet().iterator();
                        listItems.clear();
                        int i = 0;
                        while (it.hasNext())
                        {

                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry)it.next();
                            Event e = (Event )ObjectList1.get(i);
                            resultsMap.put("First Line", e.getTitle() );
                            resultsMap.put("Second Line", e.getTimeOfEvent());
                            listItems.add(resultsMap);
                            i++;
                        }

                        Iterator itdo = namedo.entrySet().iterator();
                        listdo.clear();
                        i = 0;
                        while (itdo.hasNext())
                        {

                            HashMap<String, String> resultsMap = new HashMap<>();
                            Map.Entry pair = (Map.Entry)itdo.next();
                            Event e = (Event )ObjectList2.get(i);
                            resultsMap.put("First Line", e.getTitle() );
                            resultsMap.put("Second Line", e.getDateOfEvent());
                            listdo.add(resultsMap);
                            i++;
                        }



                        today.setAdapter(ad_today);
                        upcoming.setAdapter(ad_upcoming);


                        today.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Event e = (Event) ObjectList1.get(position);
                                //Toast.makeText(getApplicationContext(), e.getTitle(),Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getLayoutInflater();
                                dialogView = inflater.inflate(R.layout.event_show_dialog_box, null);
                                dialogBuilder.setView(dialogView);
                                //spinner = dialogView.findViewById(R.id.spinner);
//                dialogView.findViewById(R.id.id0).setVisibility(View.GONE);
                                TextView t = dialogView.findViewById(R.id.TitleEvent);
                                t.setText(e.getTitle());
                                TextView desc = dialogView.findViewById(R.id.DescriptionEvent);
                                desc.setText(e.getDescription());
                                TextView date = dialogView.findViewById(R.id.DateEvent);
                                date.setText(e.getDateOfEvent());
                                TextView time = dialogView.findViewById(R.id.TimeEvent);
                                time.setText(e.getTimeOfEvent());
                                TextView venue = dialogView.findViewById(R.id.VenueEvent);
                                venue.setText(e.getVenue());
                                TextView type = dialogView.findViewById(R.id.Type);
                                type.setText(e.getType());
                                //dialogView.findViewById(R.id.id7).setVisibility(View.GONE);



                                final AlertDialog b = dialogBuilder.create();
                                b.show();
                            }
                        });

                        upcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Event e = (Event) ObjectList2.get(position);
                                //Event e = (Event) ObjectList1.get(position);
                                //Toast.makeText(getActivity(), e.getTitle(),Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getLayoutInflater();
                                dialogView2= inflater.inflate(R.layout.event_show_dialog_box, null);
                                dialogBuilder2.setView(dialogView2);
                                //spinner = dialogView2.findViewById(R.id.spinner);
//                dialogView2.findViewById(R.id.id0).setVisibility(View.GONE);
                                TextView t = dialogView2.findViewById(R.id.TitleEvent);
                                t.setText(e.getTitle());
                                TextView desc = dialogView2.findViewById(R.id.DescriptionEvent);
                                desc.setText(e.getDescription());
                                TextView date = dialogView2.findViewById(R.id.DateEvent);
                                date.setText(e.getDateOfEvent());
                                TextView time = dialogView2.findViewById(R.id.TimeEvent);
                                time.setText(e.getTimeOfEvent());
                                TextView venue = dialogView2.findViewById(R.id.VenueEvent);
                                venue.setText(e.getVenue());
                                TextView type = dialogView2.findViewById(R.id.Type);
                                type.setText(e.getType());
                                //dialogView2.findViewById(R.id.id7).setVisibility(View.GONE);
                                //Toast.makeText(getApplicationContext(), e.getTitle(),Toast.LENGTH_SHORT).show();

                                final AlertDialog b2 = dialogBuilder2.create();
                                b2.show();
                            }
                        });

                        ad_today.notifyDataSetChanged();
                        ad_upcoming.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

        /*today.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event e = (Event) ObjectList1.get(position);
                //Toast.makeText(getApplicationContext(), e.getTitle(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.event_show_dialog_box, null);
                dialogBuilder.setView(dialogView);
                //spinner = dialogView.findViewById(R.id.spinner);
//                dialogView.findViewById(R.id.id0).setVisibility(View.GONE);
                TextView t = dialogView.findViewById(R.id.TitleEvent);
                t.setText(e.getTitle());
                TextView desc = dialogView.findViewById(R.id.DescriptionEvent);
                desc.setText(e.getDescription());
                TextView date = dialogView.findViewById(R.id.DateEvent);
                date.setText(e.getDateOfEvent());
                TextView time = dialogView.findViewById(R.id.TimeEvent);
                time.setText(e.getTimeOfEvent());
                TextView venue = dialogView.findViewById(R.id.VenueEvent);
                venue.setText(e.getVenue());
                TextView type = dialogView.findViewById(R.id.Type);
                type.setText(e.getType());
                //dialogView.findViewById(R.id.id7).setVisibility(View.GONE);



                final AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });

        upcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event e = (Event) ObjectList2.get(position);
                //Event e = (Event) ObjectList1.get(position);
                //Toast.makeText(getActivity(), e.getTitle(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                dialogView2= inflater.inflate(R.layout.event_show_dialog_box, null);
                dialogBuilder2.setView(dialogView2);
                //spinner = dialogView2.findViewById(R.id.spinner);
//                dialogView2.findViewById(R.id.id0).setVisibility(View.GONE);
                TextView t = dialogView2.findViewById(R.id.TitleEvent);
                t.setText(e.getTitle());
                TextView desc = dialogView2.findViewById(R.id.DescriptionEvent);
                desc.setText(e.getDescription());
                TextView date = dialogView2.findViewById(R.id.DateEvent);
                date.setText(e.getDateOfEvent());
                TextView time = dialogView2.findViewById(R.id.TimeEvent);
                time.setText(e.getTimeOfEvent());
                TextView venue = dialogView2.findViewById(R.id.VenueEvent);
                venue.setText(e.getVenue());
                TextView type = dialogView2.findViewById(R.id.Type);
                type.setText(e.getType());
                //dialogView2.findViewById(R.id.id7).setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), e.getTitle(),Toast.LENGTH_SHORT).show();

                final AlertDialog b2 = dialogBuilder2.create();
                b2.show();
            }
        });*/





}




   /* public void todayclick(View view){

        Toast.makeText(getApplicationContext(),"chala?",Toast.LENGTH_SHORT).show();
    }*/


    /*public class ThreadAdapter extends ArrayAdapter<Thread> {
        public ThreadAdapter(Context context, List<Thread> threads) {
            super(context, 0, threads);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Thread user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_thread, parent, false);
            }
            // Lookup view for data population
            TextView messagetext = (TextView) convertView.findViewById(R.id.message_text);
            TextView messageuser = (TextView) convertView.findViewById(R.id.message_user);
            TextView messageTime = (TextView) convertView.findViewById(R.id.message_time);
            TextView messagetitle = (TextView) convertView.findViewById(R.id.message_title);

            // Populate the data into the template view using the data object
            messagetext.setText(user.getThreadContent());
            messageuser.setText(user.getUsername());
            messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", user.getDateOfCreation()));
            messagetitle.setText(user.getTitle());
            // Return the completed view to render on screen
            return convertView;
        }
    }*/








}


