package com.iitg.interaction.facultystudentinteractionportal;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CourseMainPageProf extends AppCompatActivity {

    public String filepath;
    public Uri selectedfile;


    TextView editTextName;
    TextView FileName;
    Button buttonSelectFile;
    Button buttonAddClass;

    public int flag=0;
//    public Button buttonSelectFile;



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
//        buttonSelectFile = findViewById(R.id.buttonSelectFile);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_course_main_page_prof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.AddMaterial:
                ShowUploadFileDialogBox();

//                Intent intent = new Intent(CourseMainPageProf.this,TestActivity.class);
//                startActivity(intent);
                return true;

            case R.id.NewEvent:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
                    Toast.makeText(CourseMainPageProf.this,"Download Url:"+downloadUri.toString(),Toast.LENGTH_LONG).show();
                    Log.v(TAG,"Download Url:"+downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}
