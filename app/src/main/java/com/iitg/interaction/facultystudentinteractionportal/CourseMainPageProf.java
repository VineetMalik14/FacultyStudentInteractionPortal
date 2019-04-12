package com.iitg.interaction.facultystudentinteractionportal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseMainPageProf extends AppCompatActivity {

    public String filepath;
    public Uri selectedfile;
    public ArrayList<Event> events = new ArrayList<Event>();

    TextView editTextName;
    TextView FileName;
    Button buttonSelectFile;
    Button buttonAddClass;
    private DatabaseReference databaseReference;
    public int flag=0;
    public String TitleMaterial;



    public static final String TAG="CourseMainPageProf";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask uploadTask;
    StorageReference mountainsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main_page_prof);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses").child("CS101").child("Events");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Event event = messageSnapshot.getValue(Event.class);
                    events.add(event);
                    Log.v("Title", event.getTitle());
                }
                Log.v("Size", String.valueOf(events.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        databaseReference.addValueEventListener(new  ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
//                    Event event = messageSnapshot.getValue(Event.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) { }
//        });


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

                databaseReference = FirebaseDatabase.getInstance().getReference();
                Event event = new Event(Calendar.getInstance().getTime(),DateEvent,Title,Description,TypeEvent,VenueEvent,TimeEvent);
                Toast.makeText(CourseMainPageProf.this,event.getTitle(),Toast.LENGTH_LONG).show();
                String key=databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
                databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events").child(key).setValue(event);


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
        final View dialogView = inflater.inflate(R.layout.add_file_dialog_box, null);
        dialogBuilder.setView(dialogView);
//        getFile();

        dialogBuilder.setTitle("Doraemon");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        editTextName = dialogView.findViewById(R.id.editTextName);
        FileName = dialogView.findViewById(R.id.FileName);
        buttonSelectFile = dialogView.findViewById(R.id.buttonSelectFile);
        buttonAddClass = dialogView.findViewById(R.id.buttonAddClass);

        TitleMaterial = editTextName.getText().toString();

        buttonSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                filepath = "";
                FileName.setText("");
                getFile();

            }
        });

        if (flag == 0) {
            buttonAddClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(CourseMainPageProf.this, "First Select File", Toast.LENGTH_LONG).show();
//                    uploadFile();
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



            buttonAddClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final StorageReference storageRef = storage.getReference();

                    // add course id in front of file path
                    mountainsRef = storageRef.child(selectedfile.getLastPathSegment());
                    uploadTask = mountainsRef.putFile(selectedfile);

                    // failure and success listeners
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
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

    public void getDownloadUrl(final StorageReference mountainsRef,UploadTask uploadTask)
    {
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
                    Toast.makeText(CourseMainPageProf.this,"Download Url:"+downloadUri.toString(),Toast.LENGTH_LONG).show();
                    Log.v(TAG,"Download Url:"+downloadUri.toString());
                    //----------------
                    // taking values from title and file url to be stored in firebase
                    if(TitleMaterial==""){Toast.makeText(CourseMainPageProf.this,"Please fill the title of class",Toast.LENGTH_SHORT).show();}
                    else {
                            databaseReference = FirebaseDatabase.getInstance().getReference();

                    }

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


//        StorageReference httpsReference = storage.getReferenceFromUrl(url[0]);
    }
}

//TODO 1. show all the information from course add page to course main page of prof
//TODO 2. add file along with its title in firebase database then retrieve and show downloadable url in file format in main page
//TODO 3. add events in firebase database and then show them on activity
//TODO 4. while showing all the information first check if this course already exists if yes then retrieve information from there
//TODO    because you can here from main page also of prof.
//TODO 5. give update options here on this activity
