package com.example.does;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskAct extends AppCompatActivity {

    TextView addtitle, title;
    EditText titledoes;
    Button btnSaveTask, btnCancelTask;
    DatabaseReference ref;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        addtitle =  findViewById(R.id.addtitle);
        title = findViewById(R.id.title);
        titledoes = findViewById(R.id.titledoes);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancelTask = findViewById(R.id.btnCancelTask);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Does"+doesNum);
                ref.child("titledoes").setValue(titledoes.getText().toString());
                ref.child("keydoes").setValue(keydoes);
                Intent a = new Intent(NewTaskAct.this, MainActivity.class);
                startActivity(a);
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
//                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);
//
//                        Intent a = new Intent(NewTaskAct.this, MainActivity.class);
//                        startActivity(a);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
                //});
            }
        });

        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(NewTaskAct.this, MainActivity.class);
                startActivity(a);
            }
        });
    }
}
