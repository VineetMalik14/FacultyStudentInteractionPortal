package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_signup;
    Button btn_customlogin;
    EditText etusername;
    EditText etpassword;
    FirebaseAuth firebaseAuth;
    OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

  //  OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_customlogin = findViewById(R.id.btn_logincustom);
        etpassword=findViewById(R.id.et_password);
        etusername=findViewById(R.id.et_username);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_customlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customloginfunction();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginfunction();

            }
        });


    }


    void customloginfunction()
    {
        if(etusername.getText().toString().isEmpty()||etpassword.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter your details!",Toast.LENGTH_LONG).show();
            return;
        }
        DatabaseReference ref = database.getReference("users/"+etusername.getText().toString().trim());



        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                NewUser user = dataSnapshot.getValue(NewUser.class);

                if(user==null)
                {
                    Toast.makeText(getApplicationContext(),"Username doesn't exist",Toast.LENGTH_LONG).show();
                    return;
                }

                String userpass = user.password;
                try {
                    if(userpass.equals(Sha1Custom.SHA1(etpassword.getText().toString())))
                    {
                        Toast.makeText(getApplicationContext(),"Welcome "+user.fullname,Toast.LENGTH_LONG).show();
                        UserInfo.fillUserInfo(user.username,user.fullname,user.usertype,user.rollnumber,user.email,user.occupation,user.department,user.year);

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    void loginfunction()
    {
        Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    Toast.makeText(getApplicationContext(),"Login success it was pending!!",Toast.LENGTH_LONG).show();
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "user LOGINED FAILED, PENDING  !!");
                                    Toast.makeText(getApplicationContext(),"Login Failed, it was pending!!",Toast.LENGTH_LONG).show();

                                    // Handle failure.
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            firebaseAuth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // User is signed in.
                                    // IdP data available in
                                    // authResult.getAdditionalUserInfo().getProfile().
                                    // The OAuth access token can also be retrieved:
                                    // authResult.getCredential().getAccessToken().
                                    Toast.makeText(getApplicationContext(),"Login success!!",Toast.LENGTH_LONG).show();

                                    Log.d(TAG, "user LOGINED  !!");
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);


                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "user FAILED !! "+e.toString() );
                                    Toast.makeText(getApplicationContext(),"Login Failed!!"+e.toString(),Toast.LENGTH_LONG).show();

                                    // Handle failure.
                                }
                            });
        }


    }



}
