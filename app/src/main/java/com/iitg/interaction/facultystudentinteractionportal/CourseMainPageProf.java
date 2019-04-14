package com.iitg.interaction.facultystudentinteractionportal;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
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

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class CourseMainPageProf extends AppCompatActivity {

    public String filepath;
    public Uri selectedfile;
    public ArrayList<Event> events = new ArrayList<Event>();
    public ArrayList<CourseMaterial> materials = new ArrayList<CourseMaterial>();
    TextView editTextName;
    TextView FileName;
    Button buttonSelectFile;
    Button buttonAddClass;
    private DatabaseReference databaseReference;
    public int flag=0;
    public String TitleMaterial;
    public View dialogViewFile;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    public int count=0;
    public AlertDialog mate;
    public  Notification notification;
    public static final String TAG="CourseMainPageProf";
    private ProgressBar progressBar;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask uploadTask;
    StorageReference mountainsRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main_page_prof);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // filling the values in the content page

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID"));
        TextView textView = findViewById(R.id.textView4);
        textView.setText(getIntent().getStringExtra("CourseID"));
        TextView textView1 = findViewById(R.id.textView7);
        textView1.setText(getIntent().getStringExtra("CourseTitle"));
        EditText textView2 = findViewById(R.id.editText6);
        textView2.setText(getIntent().getStringExtra("CourseDescription"));
        EditText textView3 = findViewById(R.id.editText7);
        textView3.setText(getIntent().getStringExtra("CourseSyllabus"));
        EditText textView4 = findViewById(R.id.editText8);
        textView4.setText(getIntent().getStringExtra("CourseMarks"));
        TextView textView5 = findViewById(R.id.textView15);
        textView5.setText(getIntent().getStringExtra("CourseTimeSlots"));
        TextView textView6 = findViewById(R.id.textView8);
        textView6.setText(getIntent().getStringExtra("CourseDateOfCreation"));



        //getting events as an arraylist
        // also returns zero size if events does not exist in database
        //------------------------------------------------------------------------------------
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events");
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
                ListView listView1 = findViewById(R.id.EventsList);
                final CustomAdapter1 customAdapter1 = new CustomAdapter1(CourseMainPageProf.this,events);
////                    final ThreadAdapter adapter = new ThreadAdapter(DiscussionThreads.this, threads);
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID")).child("Course Material");
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
                ListView listView = findViewById(R.id.course_material);
                final CustomAdapter customAdapter = new CustomAdapter(CourseMainPageProf.this,materials);
                listView.setAdapter(customAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // now we have  all the value that will be needed for
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        CourseMaterial item = customAdapter.getItem(position);
                        downloadFiles(CourseMainPageProf.this,item.getFileName(),DIRECTORY_DOWNLOADS,item.getURL());
                        // now send the key with the intent you are showing
                    }
                });

//                    final ThreadAdapter adapter = new ThreadAdapter(DiscussionThreads.this, threads);

//                Log.v("Size", String.valueOf(materials.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //------------------------------------------------------------------------------------


//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

//        final TextView urlText = findViewById(R.id.textView4);
//        urlText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                new DownloadFileFromURL().execute(urlText.getText().toString());
//                downloadFiles(CourseMainPageProf.this,"ABC.pdf",DIRECTORY_DOWNLOADS,urlText.getText().toString());
//            }
//        });
        findViewById(R.id.textView11).setVisibility(View.GONE);
        findViewById(R.id.editText6).setVisibility(View.GONE);
        findViewById(R.id.textView12).setVisibility(View.GONE);
        findViewById(R.id.textView9).setVisibility(View.GONE);
        findViewById(R.id.textView8).setVisibility(View.GONE);
        findViewById(R.id.editText7).setVisibility(View.GONE);
        findViewById(R.id.textView13).setVisibility(View.GONE);
        findViewById(R.id.editText8).setVisibility(View.GONE);
        findViewById(R.id.textView14).setVisibility(View.GONE);
        findViewById(R.id.textView15).setVisibility(View.GONE);
        findViewById(R.id.textView16).setVisibility(View.VISIBLE);
        findViewById(R.id.textView17).setVisibility(View.VISIBLE);
        findViewById(R.id.textView18).setVisibility(View.GONE);
        findViewById(R.id.textView25).setVisibility(View.GONE);
        findViewById(R.id.course_material).setVisibility(View.VISIBLE);
        findViewById(R.id.EventsList).setVisibility(View.VISIBLE);
        findViewById(R.id.button7).setVisibility(View.GONE);
        findViewById(R.id.button5).setVisibility(View.GONE);
        findViewById(R.id.button6).setVisibility(View.GONE);
        findViewById(R.id.button8).setVisibility(View.GONE);
        count++;

        Button button = findViewById(R.id.hiddenbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2==0){
                    findViewById(R.id.textView11).setVisibility(View.GONE);
                    findViewById(R.id.editText6).setVisibility(View.GONE);
                    findViewById(R.id.textView9).setVisibility(View.GONE);
                    findViewById(R.id.textView8).setVisibility(View.GONE);
                    findViewById(R.id.textView12).setVisibility(View.GONE);
                    findViewById(R.id.editText7).setVisibility(View.GONE);
                    findViewById(R.id.textView13).setVisibility(View.GONE);
                    findViewById(R.id.editText8).setVisibility(View.GONE);
                    findViewById(R.id.textView14).setVisibility(View.GONE);
                    findViewById(R.id.textView18).setVisibility(View.GONE);
                    findViewById(R.id.textView25).setVisibility(View.GONE);
                    findViewById(R.id.textView15).setVisibility(View.GONE);
                    findViewById(R.id.textView16).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView17).setVisibility(View.VISIBLE);
                    findViewById(R.id.course_material).setVisibility(View.VISIBLE);
                    findViewById(R.id.EventsList).setVisibility(View.VISIBLE);
                    findViewById(R.id.button7).setVisibility(View.GONE);
                    findViewById(R.id.button5).setVisibility(View.GONE);
                    findViewById(R.id.button6).setVisibility(View.GONE);
                    findViewById(R.id.button8).setVisibility(View.GONE);
                    count++;

                }
                else {
                    findViewById(R.id.textView11).setVisibility(View.VISIBLE);
                    findViewById(R.id.editText6).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView9).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView8).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView12).setVisibility(View.VISIBLE);
                    findViewById(R.id.editText7).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView13).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView18).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView25).setVisibility(View.VISIBLE);
                    findViewById(R.id.editText8).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView14).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView15).setVisibility(View.VISIBLE);
                    count++;
                }
            }
        });

        // make text boxes editable on long press
        //---------------------------------------------------------------------------------
        //making edit texts 6,7,8 non editable
        findViewById(R.id.editText8).setFocusable(false);
        findViewById(R.id.editText7).setFocusable(false);
        findViewById(R.id.editText6).setFocusable(false);
        findViewById(R.id.textView25).setFocusable(false);
//        findViewById(R.id.editText8).setClickable(false);
//        findViewById(R.id.editText7).setClickable(false);
//        findViewById(R.id.editText6).setClickable(false);

        findViewById(R.id.editText6).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ScrollView scrollView= findViewById(R.id.scrollView2);
                scrollView.requestDisallowInterceptTouchEvent(true);
                findViewById(R.id.editText6).setFocusable(false);
//                findViewById(R.id.editText6).setClickable(true);
//                findViewById(R.id.editText6).setEnabled(true);
                findViewById(R.id.editText6).setFocusableInTouchMode(true);
                findViewById(R.id.button5).setVisibility(View.VISIBLE);
                findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID")).child("description");
                        EditText editText = findViewById(R.id.editText6);
                        databaseReference.setValue(editText.getText().toString());
                        findViewById(R.id.editText6).setFocusable(false);
//                        findViewById(R.id.editText6).setClickable(false);
                        findViewById(R.id.button5).setVisibility(View.GONE);
                        Toast.makeText(CourseMainPageProf.this,"Description has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
        findViewById(R.id.editText7).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                findViewById(R.id.editText7).setFocusable(true);
                findViewById(R.id.editText7).setFocusableInTouchMode(true);
//                findViewById(R.id.editText7).setClickable(true);
//                findViewById(R.id.editText7).setEnabled(true);
                findViewById(R.id.button6).setVisibility(View.VISIBLE);
                findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID")).child("syllabus");
                        EditText editText = findViewById(R.id.editText7);
                        databaseReference.setValue(editText.getText().toString());
                        findViewById(R.id.editText7).setFocusable(false);
//                        findViewById(R.id.editText7).setClickable(false);
                        findViewById(R.id.button6).setVisibility(View.GONE);
                        Toast.makeText(CourseMainPageProf.this,"Syllabus has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
        findViewById(R.id.editText8).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                findViewById(R.id.editText8).setFocusable(true);
                findViewById(R.id.editText8).setFocusableInTouchMode(true);
//                findViewById(R.id.editText8).setClickable(true);
//                findViewById(R.id.editText8).setEnabled(true);
//                findViewById(R.id.editText8).setFocusableInTouchMode(true);
                findViewById(R.id.button7).setVisibility(View.VISIBLE);
                findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID")).child("marksDistribution");
                        EditText editText = findViewById(R.id.editText8);
                        databaseReference.setValue(editText.getText().toString());
                        findViewById(R.id.editText8).setFocusable(false);
//                        findViewById(R.id.editText8).setClickable(false);
                        findViewById(R.id.button7).setVisibility(View.GONE);
                        Toast.makeText(CourseMainPageProf.this,"Marks Distribution has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
        findViewById(R.id.textView25).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ScrollView scrollView= findViewById(R.id.scrollView2);
                scrollView.requestDisallowInterceptTouchEvent(true);
                findViewById(R.id.textView25).setFocusable(false);
//                findViewById(R.id.editText6).setClickable(true);
//                findViewById(R.id.editText6).setEnabled(true);
                findViewById(R.id.textView25).setFocusableInTouchMode(true);
                findViewById(R.id.button8).setVisibility(View.VISIBLE);
                findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getIntent().getStringExtra("CourseID")).child("courseKey");
                        EditText editText = findViewById(R.id.textView25);
                        databaseReference.setValue(editText.getText().toString());
                        findViewById(R.id.textView25).setFocusable(false);
//                        findViewById(R.id.editText6).setClickable(false);
                        findViewById(R.id.button8).setVisibility(View.GONE);
                        Toast.makeText(CourseMainPageProf.this,"Course Key has been successfully updated.",Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_course_main_page_prof, menu);
        return true;
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
        }
        return super.onOptionsItemSelected(item);
    }
    //-------------------------------------------------

    // add event dialog box
    //-------------------------------------
    private void ShowAddEventDialogBox() {

        // showing the alert box
        //-------------------
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_event_dialog_box, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog b = dialogBuilder.create();
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

                new DatePickerDialog(CourseMainPageProf.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                new DatePickerDialog(CourseMainPageProf.this,myCalendar.get(Calendar.DAY_OF_WEEK));
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
                mTimePicker = new TimePickerDialog(CourseMainPageProf.this, new TimePickerDialog.OnTimeSetListener() {
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
                if(Title.equals("") || Description.equals("") || DateEvent.equals("") || TimeEvent.equals("") || VenueEvent.equals("")){Toast.makeText(CourseMainPageProf.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();}
                else
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    Event event = new Event(Calendar.getInstance().getTime(),DateEvent,Title,Description,TypeEvent,VenueEvent,TimeEvent);
//                Toast.makeText(CourseMainPageProf.this,event.getTitle(),Toast.LENGTH_LONG).show();
                    String key=databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
                    databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events").child(key).setValue(event);
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        dialogViewFile = inflater.inflate(R.layout.add_file_dialog_box, null);
        dialogBuilder.setView(dialogViewFile);
//        getFile();

        dialogBuilder.setTitle("Doraemon");
        mate = dialogBuilder.create();
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
                    Toast.makeText(CourseMainPageProf.this, "First Select File", Toast.LENGTH_LONG).show();
//                    uploadFile();
//                    b.dismiss();
                }
            });

        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            selectedfile = data.getData(); //The uri with the location of the file
            filepath = selectedfile.getPath().toString();
           // Toast.makeText(this,filepath,Toast.LENGTH_LONG).show();
            if (filepath.equals("")){
                flag =0;
            }

            Toast.makeText(CourseMainPageProf.this,filepath,Toast.LENGTH_LONG).show();

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
                    buttonAddClass.setEnabled(false);

                    final StorageReference storageRef = storage.getReference();

                    // add course id in front of file path
                    mountainsRef = storageRef.child(selectedfile.getLastPathSegment());
                    uploadTask = mountainsRef.putFile(selectedfile);

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
                            buttonAddClass.setEnabled(true);
                            Toast.makeText(CourseMainPageProf.this,"File could not be uploaded",Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Toast.makeText(CourseMainPageProf.this,"File uploaded successfully.",Toast.LENGTH_SHORT).show();


                            //------------------------
                            // getting download url for file
                            getDownloadUrl(mountainsRef,uploadTask);

                        }
                    });
                }
            });

        }

    }

    public void getDownloadUrl(final StorageReference mountainsRef,UploadTask uploadTask) {
//        final String[] url = new String[1];
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
                        Toast.makeText(CourseMainPageProf.this, "Please fill the title of class", Toast.LENGTH_SHORT).show();
                    } else {

                        EditText ClassTitle = dialogViewFile.findViewById(R.id.editTextName);
                        TextView FileName = dialogViewFile.findViewById(R.id.FileName);
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        CourseMaterial courseMaterial = new CourseMaterial(ClassTitle.getText().toString()
                                , downloadUri.toString(), FileName.getText().toString(), Calendar.getInstance().getTime());
                        String key = databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
                        databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Course Material").child(key).setValue(courseMaterial);
                        mate.dismiss();

                    }

                } else {
                    Toast.makeText(CourseMainPageProf.this, "File could not be successfully uploaded", Toast.LENGTH_SHORT).show();
                    // Handle failures
                    // ...
                }
            }
        });


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
            dateText.setText(materials.get(position).getDate().toString());
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
            TypeText.setText("Type:" +  events.get(position).getType());
            DescriptionText.setText("Description:\n" + events.get(position).getDescription());
            VenueText.setText("Venue: "+events.get(position).getVenue());
            DateeventText.setText("Date: " + events.get(position).getDateOfEvent());
            TimeText.setText("Time: " + events.get(position).getTimeOfEvent());
            return convertView;
        }
    }

}





//TODO 1. show all the information from course add page to course main page of prof
//TODO 4. while showing all the information first check if this course already exists if yes then retrieve information from there
//TODO    because you can here from main page also of prof.
//TODO 8. make notification bar for upload file
