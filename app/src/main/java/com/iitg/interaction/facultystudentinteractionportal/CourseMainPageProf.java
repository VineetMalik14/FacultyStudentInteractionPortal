package com.iitg.interaction.facultystudentinteractionportal;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class CourseMainPageProf extends Fragment {

    public String filepath;
    public String filepath2;
    public Uri selectedfile;
    public Uri selectedfile2;
    public ArrayList<Event> events = new ArrayList<Event>();
    public ArrayList<CourseMaterial> materials = new ArrayList<CourseMaterial>();
    TextView editTextName;
    TextView FileName;
    Button buttonSelectFile;
    Button buttonAddClass;
    private DatabaseReference databaseReference;
    public int flag=0;
    int flag2 = 0;
    public String TitleMaterial;
    public View dialogViewFile;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    public int count=0;
    public int count1=0;
    public int count2=0;
    public int count3=0;
    public AlertDialog mate;
    public  Notification notification;
    public static final String TAG="CourseMainPageProf";
    private ProgressBar progressBar;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask uploadTask;
    StorageReference mountainsRef;
    AlertDialog b;
    int PROGRESS_CURRENT;
    NotificationCompat.Builder builder;
    int PROGRESS_MAX = 100;
    NotificationManager notificationManager;
    NotificationChannel channel;





    TextView FileNameProject;
    EditText title;
    EditText description;
    Button buttonSelectFileProject;
    Button buttonProjectAdd;
    View dialogView;
    EditText date_of_event;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_course_main_page_prof, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        TextView textView = getView().findViewById(R.id.textView4);
        textView.setText(getActivity().getIntent().getStringExtra("CourseID"));
        TextView textView1 = getView().findViewById(R.id.textView7);
        textView1.setText(getActivity().getIntent().getStringExtra("CourseTitle"));
        EditText textView2 = getView().findViewById(R.id.editText6);
        textView2.setText(getActivity().getIntent().getStringExtra("CourseDescription"));
        EditText textView3 = getView().findViewById(R.id.editText7);
        textView3.setText(getActivity().getIntent().getStringExtra("CourseSyllabus"));
        EditText textView4 = getView().findViewById(R.id.editText8);
        textView4.setText(getActivity().getIntent().getStringExtra("CourseMarks"));
        TextView textView5 = getView().findViewById(R.id.textView15);
        String[] timeslots = getActivity().getIntent().getStringExtra("CourseTimeSlots").split(",");
        Log.d(TAG,timeslots[0]+timeslots[1]);
        for(int i=0;i<timeslots.length;i++)
        {
            textView5.append("      "+timeslots[i]+'\n');
        }

//        textView5.setText(getActivity().getIntent().getStringExtra("CourseTimeSlots"));
        TextView textView6 = getView().findViewById(R.id.textView8);
        textView6.setText(getActivity().getIntent().getStringExtra("CourseDateOfCreation"));



        //getting events as an arraylist
        // also returns zero size if events does not exist in database
        //------------------------------------------------------------------------------------
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events = new ArrayList<Event>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Event event = messageSnapshot.getValue(Event.class);
                    events.add(event);
                    Log.v("Title", event.getTitle());
                }
                Collections.reverse(events);
                Log.v("Size", String.valueOf(events.size()));
                ListView listView1 = getView().findViewById(R.id.EventsList);
                final CustomAdapter1 customAdapter1 = new CustomAdapter1(getActivity(),events);
                listView1.setAdapter(customAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //------------------------------------------------------------------------------------

        //getting course materials as an arraylist
        // also returns zero size if events does not exist in database
        //------------------------------------------------------------------------------------
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Course Material");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 materials = new ArrayList<CourseMaterial>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    CourseMaterial material = messageSnapshot.getValue(CourseMaterial.class);
                    materials.add(material);
                    Log.v("Title", material.getTitle());

                }
                Collections.reverse(materials);
                ListView listView = getView().findViewById(R.id.course_material);
                final CustomAdapter customAdapter = new CustomAdapter(getActivity(),materials);
                listView.setAdapter(customAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        CourseMaterial item = customAdapter.getItem(position);
                        downloadFiles(getActivity(),item.getFileName(),DIRECTORY_DOWNLOADS,item.getURL());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //------------------------------------------------------------------------------------


        getView().findViewById(R.id.textView11).setVisibility(View.GONE);
        getView().findViewById(R.id.editText6).setVisibility(View.GONE);
        getView().findViewById(R.id.textView12).setVisibility(View.GONE);
        getView().findViewById(R.id.textView9).setVisibility(View.GONE);
        getView().findViewById(R.id.textView8).setVisibility(View.GONE);
        getView().findViewById(R.id.editText7).setVisibility(View.GONE);
        getView().findViewById(R.id.textView13).setVisibility(View.GONE);
        getView().findViewById(R.id.editText8).setVisibility(View.GONE);
        getView().findViewById(R.id.textView14).setVisibility(View.GONE);
        getView().findViewById(R.id.textView15).setVisibility(View.GONE);
        getView().findViewById(R.id.textView16).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.textView17).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.textView18).setVisibility(View.GONE);
        getView().findViewById(R.id.textView25).setVisibility(View.GONE);
        getView().findViewById(R.id.course_material).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.EventsList).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.button7).setVisibility(View.GONE);
        getView().findViewById(R.id.button5).setVisibility(View.GONE);
        getView().findViewById(R.id.button6).setVisibility(View.GONE);
        getView().findViewById(R.id.button8).setVisibility(View.GONE);
        getView().findViewById(R.id.course_material).setVisibility(View.GONE);
        getView().findViewById(R.id.EventsList).setVisibility(View.GONE);
        getView().findViewById(R.id.CourseProjects).setVisibility(View.GONE);
        count++;
        count1++;
        count2++;
        count3++;
        Button textView16 = getView().findViewById(R.id.textView16);
        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count1%2==1)
                {
                    getView().findViewById(R.id.course_material).setVisibility(View.VISIBLE);
                    count1++;
                }
                else
                {
                    getView().findViewById(R.id.course_material).setVisibility(View.GONE);
                    count1++;
                }

            }
        });
        Button textView17 = getView().findViewById(R.id.textView17);
        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count2%2==1)
                {
                    getView().findViewById(R.id.EventsList).setVisibility(View.VISIBLE);
                    count2++;
                }
                else
                {
                    getView().findViewById(R.id.EventsList).setVisibility(View.GONE);
                    count2++;
                }

            }
        });
        Button button1 = getView().findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count3%2==1)
                {
                    getView().findViewById(R.id.CourseProjects).setVisibility(View.VISIBLE);
                }
                else
                {
                    getView().findViewById(R.id.CourseProjects).setVisibility(View.GONE);
                }
            }
        });
        Button button = getView().findViewById(R.id.hiddenbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2==0){
                    getView().findViewById(R.id.textView11).setVisibility(View.GONE);
                    getView().findViewById(R.id.editText6).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView9).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView8).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView12).setVisibility(View.GONE);
                    getView().findViewById(R.id.editText7).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView13).setVisibility(View.GONE);
                    getView().findViewById(R.id.editText8).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView14).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView18).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView25).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView15).setVisibility(View.GONE);
                    getView().findViewById(R.id.textView16).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView17).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.course_material).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.EventsList).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.button7).setVisibility(View.GONE);
                    getView().findViewById(R.id.button5).setVisibility(View.GONE);
                    getView().findViewById(R.id.button6).setVisibility(View.GONE);
                    getView().findViewById(R.id.button8).setVisibility(View.GONE);
                    count++;

                }
                else {
                    getView().findViewById(R.id.textView11).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.editText6).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView9).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView8).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView12).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.editText7).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView13).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView18).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView25).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.editText8).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView14).setVisibility(View.VISIBLE);
                    getView().findViewById(R.id.textView15).setVisibility(View.VISIBLE);
                    count++;
                }
            }
        });

        // make text boxes editable on long press
        //---------------------------------------------------------------------------------
        //making edit texts 6,7,8 non editable
        getView().findViewById(R.id.editText8).setFocusable(false);
        getView().findViewById(R.id.editText7).setFocusable(false);
        getView().findViewById(R.id.editText6).setFocusable(false);
        getView().findViewById(R.id.textView25).setFocusable(false);

        getView().findViewById(R.id.editText6).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ScrollView scrollView= getView().findViewById(R.id.scrollView2);
                scrollView.requestDisallowInterceptTouchEvent(true);
                getView().findViewById(R.id.editText6).setFocusable(false);
                getView().findViewById(R.id.editText6).setFocusableInTouchMode(true);
                getView().findViewById(R.id.button5).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("description");
                        EditText editText = getView().findViewById(R.id.editText6);
                        databaseReference.setValue(editText.getText().toString());
                        getView().findViewById(R.id.editText6).setFocusable(false);
                        getView().findViewById(R.id.button5).setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Description has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
        getView().findViewById(R.id.editText7).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getView().findViewById(R.id.editText7).setFocusable(true);
                getView().findViewById(R.id.editText7).setFocusableInTouchMode(true);
                getView().findViewById(R.id.button6).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("syllabus");
                        EditText editText = getView().findViewById(R.id.editText7);
                        databaseReference.setValue(editText.getText().toString());
                        getView().findViewById(R.id.editText7).setFocusable(false);
                        getView().findViewById(R.id.button6).setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Syllabus has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
        getView().findViewById(R.id.editText8).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getView().findViewById(R.id.editText8).setFocusable(true);
                getView().findViewById(R.id.editText8).setFocusableInTouchMode(true);
                getView().findViewById(R.id.button7).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("marksDistribution");
                        EditText editText = getView().findViewById(R.id.editText8);
                        databaseReference.setValue(editText.getText().toString());
                        getView().findViewById(R.id.editText8).setFocusable(false);
                        getView().findViewById(R.id.button7).setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Marks Distribution has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
        getView().findViewById(R.id.textView25).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ScrollView scrollView= getView().findViewById(R.id.scrollView2);
                scrollView.requestDisallowInterceptTouchEvent(true);
                getView().findViewById(R.id.textView25).setFocusable(false);
                getView().findViewById(R.id.textView25).setFocusableInTouchMode(true);
                getView().findViewById(R.id.button8).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("courseKey");
                        EditText editText = getView().findViewById(R.id.textView25);
                        databaseReference.setValue(editText.getText().toString());
                        getView().findViewById(R.id.textView25).setFocusable(false);
                        getView().findViewById(R.id.button8).setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Course Key has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_course_main_page_prof, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    // on selecting any one of the options
    //-------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.AddMaterial:
                ShowUploadFileDialogBox();
                return true;
            case R.id.NewEvent:
                ShowAddEventDialogBox();
                return true;
            case R.id.AddProject:
                ShowAddProjectDialogBox();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowAddProjectDialogBox(){
        flag2 = 0;
        selectedfile = null;


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.add_project_dialog_box, null);
        dialogBuilder.setView(dialogView);

        b = dialogBuilder.create();
        b.show();


        description = dialogView.findViewById(R.id.descriptionofproject);
        FileNameProject = dialogView.findViewById(R.id.FIleNameProject);
        buttonSelectFileProject = dialogView.findViewById(R.id.buttonSelectFileProject);
        buttonProjectAdd = dialogView.findViewById(R.id.buttonProjectAdd);

        title = dialogView.findViewById(R.id.titleofproject);
        date_of_event = dialogView.findViewById(R.id.Deadline);






        final Calendar myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.HOUR,hour);
//                myCalendar.set(Calendar.MINUTE,minutes);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date_of_event.setText(sdf.format(myCalendar.getTime()));
            }


        };

        date_of_event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                new DatePickerDialog(CourseMainPageProf.this,myCalendar.get(Calendar.DAY_OF_WEEK));
            }
        });



        buttonSelectFileProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepath2 = "";
                flag2 = 1;
                FileNameProject.setText("");
                getFile2();
            }
        });



        if(flag2 == 0){
            buttonProjectAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "First select a file", Toast.LENGTH_SHORT).show();

                }
            });

        }


    }


    //-------------------------------------------------

    // add event dialog box
    //-------------------------------------
    private void ShowAddEventDialogBox() {

        // showing the alert box
        //-------------------
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_event_dialog_box, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        b.show();

        // -----------------------------

        // showing calender on clicking date edit text box to select date
        //------------------------
        final Calendar myCalendar = Calendar.getInstance();
        final EditText date_of_event = dialogView.findViewById(R.id.DateEvent);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.HOUR,hour);
//                myCalendar.set(Calendar.MINUTE,minutes);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date_of_event.setText(sdf.format(myCalendar.getTime()));
            }


        };

        date_of_event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                new DatePickerDialog(getActivity(),myCalendar.get(Calendar.DAY_OF_WEEK));
            }
        });

        //-----------------------------------
        // show time select dialog box on clicking time edit box
        //------------------------------
        final EditText time_of_event = dialogView.findViewById(R.id.TimeEvent);
        time_of_event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                Calendar mcurrentTime = Calendar.getInstance();
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_of_event.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        //-------------------------------------------------


        Button addEventBtn = dialogView.findViewById(R.id.AddEventButton);


        //-------------------------------------------------

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting all the fields from alert box
                //-------------------------------------------------
                EditText EditTitle = dialogView.findViewById(R.id.TitleEvent);
                EditText EditDescription = dialogView.findViewById(R.id.DescriptionEvent);
                EditText Editvenue = dialogView.findViewById(R.id.VenueEvent);
                Spinner SpinnerType = dialogView.findViewById(R.id.TypeEvent);

                final String Title = EditTitle.getText().toString();
                final String Description = EditDescription.getText().toString();
                final String DateEvent = date_of_event.getText().toString();
                final String TimeEvent = time_of_event.getText().toString();
                final String VenueEvent = Editvenue.getText().toString();
                final String TypeEvent = SpinnerType.getSelectedItem().toString();
                if(Title.equals("") || Description.equals("") || DateEvent.equals("") || TimeEvent.equals("") || VenueEvent.equals("")){Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();}
                else
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    Event event = new Event(Calendar.getInstance().getTime(),DateEvent,Title,Description,TypeEvent,VenueEvent,TimeEvent);
//                Toast.makeText(getActivity(),event.getTitle(),Toast.LENGTH_LONG).show();
                    String key=databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
                    databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Events").child(key).setValue(event);
                    b.dismiss();
                }


            }
        });


    }

    // adding files to database
    //-----------------------------------------------------------------
    public void ShowUploadFileDialogBox() {
        flag = 0;
        selectedfile = null;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        dialogViewFile = inflater.inflate(R.layout.add_file_dialog_box, null);
        dialogBuilder.setView(dialogViewFile);
//        getFile();

        dialogBuilder.setTitle("Doraemon");
        mate = dialogBuilder.create();
        mate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mate.show();

        editTextName = dialogViewFile.findViewById(R.id.editTextName);
        FileName = dialogViewFile.findViewById(R.id.FileName);
        buttonSelectFile = dialogViewFile.findViewById(R.id.buttonSelectFile);
        buttonAddClass = dialogViewFile.findViewById(R.id.buttonAddClass);

        TitleMaterial = editTextName.getText().toString();

        buttonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                filepath = "";
                FileName.setText("");
                getFile();
//                b.dismiss();

            }
        });

        if (flag == 0) {
            buttonAddClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "First Select File", Toast.LENGTH_LONG).show();
//                    uploadFile();
//                    b.dismiss();
                }
            });

        }
    }


    public void getFile2()
    {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);
//        intent.putExtra("Filename",);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 321);


    }




    public void getFile()
    {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);
//        intent.putExtra("Filename",);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            selectedfile = data.getData(); //The uri with the location of the file
            filepath = selectedfile.getPath().toString();
           // Toast.makeText(this,filepath,Toast.LENGTH_LONG).show();
            if (filepath.equals("")){
                flag =0;
            }

            Toast.makeText(getActivity(),filepath,Toast.LENGTH_LONG).show();

            FileName.setText(filepath);






            // notification for upload progress
            //---------------------------------------
//            final Integer notificationID = 100;
//
//            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            //Set notification information:
//            final Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext());
//            notificationBuilder.setOngoing(true)
//                    .setContentTitle("Notification Content Title")
//                    .setContentText("Notification Content Text")
//                    .setSmallIcon(android.R.drawable.stat_sys_upload)
//                    .setProgress(100, 0, false);
//
//            //Send the notification:
//             notification = notificationBuilder.build();
//            notificationManager.notify(notificationID, notification);
            //-------------------------------------------------------------
            buttonAddClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                   // notificationManager = NotificationManagerCompat.from(CourseMainPageProf.this);
                    notificationManager = (NotificationManager)(getActivity().getSystemService(Context.NOTIFICATION_SERVICE));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(
                                "CHANNEL_ID",
                                "My App",
                                NotificationManager.IMPORTANCE_DEFAULT
                        );
                        notificationManager.createNotificationChannel(channel);
                    };



                    builder = new NotificationCompat.Builder(getActivity(), "CHANNEL_ID");
                    builder.setContentTitle("Upload")
                            .setContentText("Upload in progress")
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                            .setPriority(NotificationCompat.PRIORITY_MAX);

                    PROGRESS_CURRENT = 0;
                    builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
                    notificationManager.notify(0, builder.build());







                    notificationManager.notify(0, builder.build());









                    buttonAddClass.setEnabled(false);

                    final StorageReference storageRef = storage.getReference();

                    // add course id in front of file path
                    mountainsRef = storageRef.child(selectedfile.getLastPathSegment());
                    uploadTask = mountainsRef.putFile(selectedfile);

                    // Observe state change events such as progress, pause, and resume
//                    final ProgressBar finalProgressBar = progressBar;
//                    progressBar = getView().findViewById(R.id.progressBar);
                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            PROGRESS_CURRENT = (int)progress;
                            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
                            notificationManager.notify(0, builder.build());
//                            progressBar.setProgress((int) progress);
//                            Toast.makeText(getActivity(),"Upload is : "+ progress + "% done",Toast.LENGTH_LONG).show();
                            //Update notification information:
//                            notificationBuilder.setProgress(100, (int) progress, false);
//
//                            //Send the notification:
//                            notification = notificationBuilder.build();
//                            notificationManager.notify(notificationID, notification);


                            System.out.println("Upload is " + progress + "% done");
                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload is paused");
                        }
                    });


                    // failure and success listeners
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            buttonAddClass.setEnabled(true);
                            Toast.makeText(getActivity(),"File could not be uploaded",Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            builder.setContentText("Upload complete")
                                    .setProgress(0,0,false);
                            notificationManager.notify(0, builder.build());
                            Toast.makeText(getContext(),"File uploaded successfully.",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(),"File uploaded successfully.",Toast.LENGTH_SHORT).show();


                            //------------------------
                            // getting download url for file
                            getDownloadUrl(mountainsRef,uploadTask, 0);

                        }
                    });
                }
            });

        }


        if(requestCode == 321 && resultCode == RESULT_OK){


            selectedfile2 = data.getData(); //The uri with the location of the file
            filepath2 = selectedfile2.getPath().toString();
            // Toast.makeText(this,filepath,Toast.LENGTH_LONG).show();
            if (filepath2.equals("")){
                flag2 =0;
                return;
            }

            Toast.makeText(getActivity(),filepath2,Toast.LENGTH_LONG).show();

            FileNameProject.setText(filepath2);

            // notification for upload progress
            //---------------------------------------
//            final Integer notificationID = 100;
//
//            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            //Set notification information:
//            final Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext());
//            notificationBuilder.setOngoing(true)
//                    .setContentTitle("Notification Content Title")
//                    .setContentText("Notification Content Text")
//                    .setSmallIcon(android.R.drawable.stat_sys_upload)
//                    .setProgress(100, 0, false);
//
//            //Send the notification:
//             notification = notificationBuilder.build();
//            notificationManager.notify(notificationID, notification);
            //-------------------------------------------------------------
            buttonProjectAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonProjectAdd.setEnabled(false);

                    final StorageReference storageRef = storage.getReference();

                    // add course id in front of file path
                    mountainsRef = storageRef.child(selectedfile2.getLastPathSegment());
                    uploadTask = mountainsRef.putFile(selectedfile2);

                    // Observe state change events such as progress, pause, and resume
//                    final ProgressBar finalProgressBar = progressBar;
//                    progressBar = findViewById(R.id.progressBar);
                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                            progressBar.setProgress((int) progress);
//                            Toast.makeText(CourseMainPageProf.this,"Upload is : "+ progress + "% done",Toast.LENGTH_LONG).show();
                            //Update notification information:
//                            notificationBuilder.setProgress(100, (int) progress, false);
//
//                            //Send the notification:
//                            notification = notificationBuilder.build();
//                            notificationManager.notify(notificationID, notification);


                            System.out.println("Upload is " + progress + "% done");
                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload is paused");
                        }
                    });


                    // failure and success listeners
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            buttonProjectAdd.setEnabled(true);
                            Toast.makeText(getActivity(),"File could not be uploaded",Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Toast.makeText(getActivity(),"File uploaded successfully.",Toast.LENGTH_SHORT).show();


                            //------------------------
                            // getting download url for file
                            getDownloadUrl(mountainsRef,uploadTask,1);

                        }
                    });
                }
            });



        }




    }

//    public void getDownloadUrl(final StorageReference mountainsRef,UploadTask uploadTask) {
////        final String[] url = new String[1];
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//
//                // Continue with the task to get the download URL
//                return mountainsRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
////                    url[0] = downloadUri.toString();
////                    Toast.makeText(getActivity(), "Download Url:" + downloadUri.toString(), Toast.LENGTH_LONG).show();
////                    Log.v(TAG, "Download Url:" + downloadUri.toString());
//                    //----------------
//                    // taking values from title and file url to be stored in firebase
//                    if (TitleMaterial == "") {
//                        Toast.makeText(getActivity(), "Please fill the title of class", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        EditText ClassTitle = dialogViewFile.findViewById(R.id.editTextName);
//                        TextView FileName = dialogViewFile.findViewById(R.id.FileName);
//                        databaseReference = FirebaseDatabase.getInstance().getReference();
//                        CourseMaterial courseMaterial = new CourseMaterial(ClassTitle.getText().toString()
//                                , downloadUri.toString(), FileName.getText().toString(), Calendar.getInstance().getTime());
//                        String key = databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
//                        databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Course Material").child(key).setValue(courseMaterial);
//                        mate.dismiss();
//
//                    }
//
//                } else {
//                    Toast.makeText(getActivity(), "File could not be successfully uploaded", Toast.LENGTH_SHORT).show();
//                    // Handle failures
//                    // ...
//                }
//            }
//        });
//
//
//    }

    public void getDownloadUrl(final StorageReference mountainsRef,UploadTask uploadTask, int code) {
//        final String[] url = new String[1];

        if (code == 0){

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return mountainsRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
//                    url[0] = downloadUri.toString();
//                    Toast.makeText(CourseMainPageProf.this, "Download Url:" + downloadUri.toString(), Toast.LENGTH_LONG).show();
//                    Log.v(TAG, "Download Url:" + downloadUri.toString());
                        //----------------
                        // taking values from title and file url to be stored in firebase
                        if (TitleMaterial == "") {
                            Toast.makeText(getActivity(), "Please fill the title of class", Toast.LENGTH_SHORT).show();
                        } else {

                            EditText ClassTitle = dialogViewFile.findViewById(R.id.editTextName);
                            TextView FileName = dialogViewFile.findViewById(R.id.FileName);
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            CourseMaterial courseMaterial = new CourseMaterial(ClassTitle.getText().toString()
                                    , downloadUri.toString(), FileName.getText().toString(), Calendar.getInstance().getTime());
                            String key = databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
                            databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("Course Material").child(key).setValue(courseMaterial);
                            mate.dismiss();

                        }

                    } else {
                        Toast.makeText(getActivity(), "File could not be successfully uploaded", Toast.LENGTH_SHORT).show();
                        // Handle failures
                        // ...
                    }
                }
            });

        }

        if (code == 1){


            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return mountainsRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
//                    url[0] = downloadUri.toString();
//                    Toast.makeText(CourseMainPageProf.this, "Download Url:" + downloadUri.toString(), Toast.LENGTH_LONG).show();
//                    Log.v(TAG, "Download Url:" + downloadUri.toString());
                        //----------------
                        // taking values from title and file url to be stored in firebase
                        if(title.getText().toString().equals("")||description.getText().toString().equals("")||date_of_event.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Please Fill All The Fields", Toast.LENGTH_SHORT).show();

                        } else {

                            TextView FileName = dialogView.findViewById(R.id.FIleNameProject);
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            CourseProject project = new CourseProject(title.getText().toString(), downloadUri.toString(), FileName.getText().toString(), date_of_event.getText().toString(), description.getText().toString());
                            String key = databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("CourseProject").push().getKey();
                            databaseReference.child("Courses").child(getActivity().getIntent().getStringExtra("CourseID")).child("CourseProject").child(key).setValue(project);
                            b.dismiss();

                        }

                    } else {
                        Toast.makeText(getActivity(), "File could not be successfully uploaded", Toast.LENGTH_SHORT).show();
                        // Handle failures
                        // ...
                    }
                }
            });





        }



    }


    public void downloadFiles(Context context, String Filename,String FileDestination, String url)
    {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, FileDestination, Filename);
        downloadManager.enqueue(request);

//
    }

    class CustomAdapter extends ArrayAdapter<CourseMaterial> {

        public CustomAdapter(Context context, ArrayList<CourseMaterial> threads) {
            super(context, 0, threads);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.course_list_main_page_prof,null);
            TextView TitleView = convertView.findViewById(R.id.textView19);
            TextView fileText = convertView.findViewById(R.id.textView20);
            TextView dateText = convertView.findViewById(R.id.textView21);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list_main_page_prof, parent, false);
            }
            TitleView.setText(materials.get(position).getTitle());
            fileText.setText(materials.get(position).getFileName());
            dateText.setText("Date:" + materials.get(position).getDate().toString());
            return convertView;
        }
    }

    class CustomAdapter1 extends ArrayAdapter<Event> {

        public CustomAdapter1(Context context, ArrayList<Event> threads) {
            super(context, 0, threads);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.course_list_events_prof,null);
            TextView TitleView = convertView.findViewById(R.id.textView19);
            TextView datecreationText = convertView.findViewById(R.id.textView10);
            TextView TypeText = convertView.findViewById(R.id.textView22);
            TextView DescriptionText = convertView.findViewById(R.id.textView20);
            TextView VenueText = convertView.findViewById(R.id.textView21);
            TextView DateeventText = convertView.findViewById(R.id.textView23);
            TextView TimeText = convertView.findViewById(R.id.textView24);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list_events_prof, parent, false);
            }
            TitleView.setText(events.get(position).getTitle());
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = events.get(position).getDateOfCreation();
            try {
                Date todayWithZeroTime = formatter.parse(formatter.format(date));
                datecreationText.setText("Created on " +  todayWithZeroTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TypeText.setText(   events.get(position).getType());
            DescriptionText.setText("Description:\n" + events.get(position).getDescription());
            VenueText.setText("Venue: "+events.get(position).getVenue());
            DateeventText.setText( events.get(position).getDateOfEvent());
            TimeText.setText( events.get(position).getTimeOfEvent());
            return convertView;
        }
    }

}





//TODO 1. show all the information from course add page to course main page of prof
//TODO 2. while showing all the information first check if this course already exists if yes then retrieve information from there
//TODO    because you can here from main page also of prof.
//TODO 3. make notification bar for upload file
//TODO 4. parse time slots in course main page for students and prof
//TODO 5. different course main pages for students and prof from Main Activity page
//TODO 6. parse the filenames
//TODO 7. create polls which are visible only to students
//TODO 8. see all the back buttons
//TODO 9. convert course add content to send to course main page using fragment
//TODO 10. not able to add new courses in professors(user) list
//TODO 11. make the prof fill the contents of the course
//TODO 12. remove barney from aman's code
//TODO 13. back button after logout
//TODO 14. disable login button after clicking once
//TODO 15. do not allow prof to enroll in the course
