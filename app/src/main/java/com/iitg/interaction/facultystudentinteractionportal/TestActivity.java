package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TestActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    //uploadTask = mountainsRef.putStream(stream);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            StorageReference storageRef = storage.getReference();

            // add course id in front of file path
            StorageReference mountainsRef = storageRef.child(selectedfile.getLastPathSegment());
            UploadTask uploadTask = mountainsRef.putFile(selectedfile);

            // failure and success listeners
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });


        }


    }
}
