package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TestActivity extends AppCompatActivity {

    public static final String TAG="TestActivity";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask uploadTask;
    StorageReference mountainsRef;
    //uploadTask = mountainsRef.putStream(stream);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            Toast.makeText(this,selectedfile.getPath(),Toast.LENGTH_LONG).show();


            // storage reference
            final StorageReference storageRef = storage.getReference();

            // add course id in front of file path
            mountainsRef = storageRef.child(selectedfile.getLastPathSegment());
            uploadTask = mountainsRef.putFile(selectedfile);

            // failure and success listeners
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(TestActivity.this,"File could not be uploaded",Toast.LENGTH_SHORT).show();
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Toast.makeText(TestActivity.this,"File uploaded successfully.",Toast.LENGTH_SHORT).show();


                    //------------------------
                    // getting download url for file
                    getDownloadUrl(mountainsRef,uploadTask);

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
                    Toast.makeText(TestActivity.this,"Download Url:"+downloadUri.toString(),Toast.LENGTH_LONG).show();
                    Log.v(TAG,"Download Url:"+downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}
