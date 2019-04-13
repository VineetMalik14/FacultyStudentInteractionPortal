package com.iitg.interaction.facultystudentinteractionportal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

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
    public View dialogViewFile;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;


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


        //getting events as an arraylist
        // also returns zero size if events does not exist in database
        //------------------------------------------------------------------------------------
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
        //------------------------------------------------------------------------------------

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final TextView urlText = findViewById(R.id.textView4);
        urlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DownloadFileFromURL().execute(urlText.getText().toString());
                downloadFiles(CourseMainPageProf.this,"ABC.pdf",DIRECTORY_DOWNLOADS,urlText.getText().toString());
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
        dialogViewFile = inflater.inflate(R.layout.add_file_dialog_box, null);
        dialogBuilder.setView(dialogViewFile);
//        getFile();

        dialogBuilder.setTitle("Doraemon");
        final AlertDialog b = dialogBuilder.create();
        b.show();

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

                        EditText ClassTitle = dialogViewFile.findViewById(R.id.editTextName);
                        TextView FileName = dialogViewFile.findViewById(R.id.FileName);
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        CourseMaterial courseMaterial = new CourseMaterial(ClassTitle.getText().toString()
                                ,downloadUri.toString(), FileName.getText().toString(),Calendar.getInstance().getTime());
                        String key=databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Events").push().getKey();
                        databaseReference.child("Courses").child(getIntent().getStringExtra("CourseID")).child("Course Material").child(key).setValue(courseMaterial);

                    }

                } else {
                    Toast.makeText(CourseMainPageProf.this,"File could not be successfully uploaded",Toast.LENGTH_SHORT).show();
                    // Handle failures
                    // ...
                }
            }
        });


//        StorageReference httpsReference = storage.getReferenceFromUrl(url[0]);
    }
//    class DownloadFileFromURL extends AsyncTask<String, String, String> {
//        public void DownloadFileFromURL() {
//
//            try {
//                URL u = new URL("http://www.qwikisoft.com/demo/ashade/20001.kml");
//                InputStream is = u.openStream();
//
//                DataInputStream dis = new DataInputStream(is);
//
//                byte[] buffer = new byte[1024];
//                int length;
//
//                FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "data/test.kml"));
//                while ((length = dis.read(buffer)) > 0) {
//                    fos.write(buffer, 0, length);
//                }
//
//            } catch (MalformedURLException mue) {
//                Log.e("SYNC getUpdate", "malformed url error", mue);
//            } catch (IOException ioe) {
//                Log.e("SYNC getUpdate", "io error", ioe);
//            } catch (SecurityException se) {
//                Log.e("SYNC getUpdate", "security error", se);
//            }
//        }
//    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case progress_bar_type: // we set this to 0
//                pDialog = new ProgressDialog(this);
//                pDialog.setMessage("Downloading file. Please wait...");
//                pDialog.setIndeterminate(false);
//                pDialog.setMax(100);
//                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                pDialog.setCancelable(true);
//                pDialog.show();
//                return pDialog;
//            default:
//                return null;
//        }
//    }
//
//    /**
//     * Background Async Task to download file
//     * */
//    class DownloadFileFromURL extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Bar Dialog
//         * */

//
//        /**
//         * Downloading file in background thread
//         * */
//        @Override
//        protected String doInBackground(String... f_url) {
//            int count;
//            try {
//                URL url = new URL(f_url[0]);
//                URLConnection conection = url.openConnection();
//                conection.connect();
//
//                // this will be useful so that you can show a tipical 0-100%
//                // progress bar
//                int lenghtOfFile = conection.getContentLength();
//
//                // download the file
//                InputStream input = new BufferedInputStream(url.openStream(),
//                        8192);
//
//                // Output stream
//                OutputStream output = new FileOutputStream(Environment
//                        .getExternalStorageDirectory().toString());
//
//                byte data[] = new byte[1024];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//            }
//
//            return null;
//        }
//
//        /**
//         * Updating progress bar
//         * */
//        protected void onProgressUpdate(String... progress) {
//            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
//        }
////
////        /**
////         * After completing background task Dismiss the progress dialog
////         * **/
//        @Override
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog after the file was downloaded
//            dismissDialog(progress_bar_type);
////
//        }
//
//    }
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
}





//TODO 1. show all the information from course add page to course main page of prof
//TODO 2. add file along with its title in firebase database then retrieve and show downloadable url in file format in main page
//TODO 3. add events in firebase database and then show them on activity
//TODO 4. while showing all the information first check if this course already exists if yes then retrieve information from there
//TODO    because you can here from main page also of prof.
//TODO 5. give update options here on this activity
//TODO 6. if cannot get download url then delete the file from firebase storage
//TODO 7. add progress bar in file download
