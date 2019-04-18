package com.example.does;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class EditTaskAct extends AppCompatActivity {

    EditText titledo;
    Button btnUpdateTask, btnDeleteTask;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        titledo = findViewById(R.id.titleDoes);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        btnDeleteTask = findViewById(R.id.btnDeleteTask);

        titledo.setText(getIntent().getStringExtra("titledoes"));
        final String keykeydoes = getIntent().getStringExtra("keydoes");

        ref = FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Does"+keykeydoes);

        btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent b = new Intent(EditTaskAct.this ,MainActivity.class);
                            startActivity(b);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Failure!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Integer doesNum = new Random().nextInt();
//                            final String key = Integer.toString(doesNum);
//                            ref = FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Does"+doesNum);
//                            ref.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    dataSnapshot.getRef().child("titledoes").setValue(titledo.getText().toString());
//                                    dataSnapshot.getRef().child("keydoes").setValue(key.toString());
//
//                                    Intent a = new Intent(EditTaskAct.this, MainActivity.class);
//                                    startActivity(a);
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    Toast.makeText(getApplicationContext(),"Failure!!",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                    }
//                });

                ref.child("titledoes").setValue(titledo.getText().toString());
                Intent aa = new Intent(EditTaskAct.this, MainActivity.class);
                        startActivity(aa);
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("titledoes").setValue(titledo.getText().toString());
//                        dataSnapshot.getRef().child("keydoes").setValue(keykeydoes);
//
//                        Intent aa = new Intent(EditTaskAct.this, MainActivity.class);
//                        startActivity(aa);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
    }
}
