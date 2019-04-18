package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class LoginActivity<scopes> extends AppCompatActivity {

    Button btn_login;
    Button btn_signup;
    Button btn_customlogin;
    EditText etusername;
    EditText etpassword;
    FirebaseAuth firebaseAuth;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            btn_login.setEnabled(true);
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            btn_customlogin.setEnabled(true);
        }
    };


    OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public SharedPreferences preferences;

  //  OAuthProvider.Builder provider = OAuthProvider.newBuilder("microsoft.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("settings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        List<String> scopes =
                new ArrayList<String>() {
                    {
                        add("mail.read");
                        add("mail.send");
                        add("mail.ReadWrite");
                        add("User.ReadBasic.All");
                        // add("User.Read.All");
                        // add("calendars.read");
                    }
                };
        provider.setScopes(scopes);



        try{
            if(false)
            if(preferences.getBoolean("logined",false) && preferences.getString("username",null)!=null)
            {

               DatabaseReference dataref = database.getReference();
                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        NewUser user=dataSnapshot.child("users").child(preferences.getString("username",null)).getValue(NewUser.class);
                        if(user == null)
                        {

                            Log.d("debug","ERROR Already logged in but user is null ");
                            preferences.edit().putBoolean("logined",false);
                            Intent intent  = getIntent();
                            startActivity(intent);
                            LoginActivity.this.finish();
                            return;
                        }

                        UserInfo.fillUserInfo(user.username,user.fullname,user.usertype,user.rollnumber,user.email,user.occupation,user.department,user.year , user.courses);
                        Log.d("debug","Already logined, FILLED USER INFO" );

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(LoginActivity.this,home.class);
                startActivity(intent);
                this.finish();


            }
        }
        catch (NullPointerException e)
        {
            Log.d("debug","already logined error "+e.toString());
        }



        setContentView(R.layout.login_screen);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_customlogin = findViewById(R.id.btn_logincustom);
        etpassword=findViewById(R.id.et_password);
        etusername=findViewById(R.id.et_username);
        firebaseAuth = FirebaseAuth.getInstance();


        if(preferences.getString("username",null)!=null)
        {
            etusername.setText(preferences.getString("username",null));
        }
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
                btn_customlogin.setEnabled(false);
                handler.postDelayed(runnable2,10000);
                customloginfunction();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                handler.postDelayed(runnable,10000);
                //btn_login.setEnabled(true);

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
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        UserInfo.fillUserInfo(user.username,user.fullname,user.usertype,user.rollnumber,user.email,user.occupation,user.department,user.year , user.courses);


                        preferences = getSharedPreferences("settings",Context.MODE_PRIVATE);
                        final SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("logined",true);
                        editor.putString("username",UserInfo.username);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this,home.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
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



    void getvaluesfromoutlook(final FirebaseUser loginuser, final AuthResult authResult)
    {

        Log.d("checkinside","I AM IN THE GET VALUES FUNCTION !!");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final String musername = loginuser.getEmail().replace("@iitg.ac.in","");
        DatabaseReference dataref = database.child("users").child(musername);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Log.d("checkinside","INSIDE OVERRIDE FUNCTION !!!!!"+dataSnapshot.hasChild(musername));
                NewUser user = dataSnapshot.getValue(NewUser.class);

                if(user==null) {
                    //create new user
                //    Map<String,Object> mapuser =authResult.getAdditionalUserInfo().getProfile();
//
//                    try {
//
//                        user = new NewUser(musername,loginuser.getDisplayName(),"usertype","roll number",loginuser.getEmail().toString(),"occupation","department","year",Sha1Custom.SHA1("password"));
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }

                    UserInfo.fullname = loginuser.getDisplayName();
                    UserInfo.email = loginuser.getEmail();
                    try {
                        UserInfo.occupation = authResult.getAdditionalUserInfo().getProfile().get("jobTitle").toString();
                    }
                    catch (NullPointerException e)
                    {
                        Log.d("debug","no jobtitle"+ e.toString());
                    }
                    String rollno=authResult.getAdditionalUserInfo().getProfile().get("surname").toString();
                    try {
                        UserInfo.rollnumber = String.valueOf(Integer.parseInt(rollno));
                    }
                    catch (NumberFormatException e)
                    {
                        Log.d("debug",e.toString());
                    }

                    UserInfo.profilepicurl = authResult.getUser().getPhotoUrl();
                    Map<String,Object> uu=authResult.getAdditionalUserInfo().getProfile();

                    for (String s : authResult.getAdditionalUserInfo().getProfile().keySet())
                    {
                        Log.d("printing ",s);
                        Log.d("printing",uu.get(s).toString());
                    }
                    Log.d("printing","department " + uu.get("department"));
                    authResult.getAdditionalUserInfo().getProfile().entrySet();
                    for (Map.Entry<String,Object> s : authResult.getAdditionalUserInfo().getProfile().entrySet()) {

                        Log.d("printing",s.getKey() + s.getValue());

                    }

                    UserInfo.profilepicurl=Uri.parse(uu.get("@odata.context").toString());

                    Log.d("DEBUG","NAME AND EMAIL ENTERED IN USERINFO");

                    SignupActivity.outlookuser=true;
                    Log.d("DEBUG","NAME AND EMAIL ENTERED IN USERINFO");

                    Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                    startActivity(intent);

//                    dataref.setValue(user);
//                    Toast.makeText(getApplicationContext(),"Registered Successfully!",Toast.LENGTH_LONG).show();

                }
                else
                {
                    user=dataSnapshot.getValue(NewUser.class);
                    Toast.makeText(getApplicationContext(),"Logined !",Toast.LENGTH_LONG).show();
                    UserInfo.profilepicurl=loginuser.getPhotoUrl();
                    UserInfo.fillUserInfo(user.username,user.fullname,user.usertype,user.rollnumber,user.email,user.occupation,user.department,user.year,user.courses);
                    Object username = authResult.getAdditionalUserInfo().getProfile().get("jobTitle");
                    Map<String,Object> addinfo = authResult.getAdditionalUserInfo().getProfile();
//                    String add_depart=addinfo.get("department").toString();
                    Object profilephoto = addinfo.get("photo");
                    authResult.getAdditionalUserInfo().getProfile();
                    for (String s : authResult.getAdditionalUserInfo().getProfile().keySet())
                    {
                        Log.d("printing ",s);
                    }
                    Object metadat = addinfo.get("@odata.context");
                    Log.d("printing","username " + metadat.toString() );

                    UserInfo.profilepicurl = Uri.parse(metadat.toString());

                    authResult.getAdditionalUserInfo().getProfile();
               //     Object companyname = addinfo.get("country");
                   Log.d("printing","username " + metadat.toString() );


                    Intent intent = new Intent(LoginActivity.this,home.class);
                    startActivity(intent);

                }


                // SAVING USERNAME OF USER INSIDE APP TO REMEMBER LATER
                preferences = getSharedPreferences("settings",Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("logined",true);
                editor.putString("username",UserInfo.username);
                editor.apply();
                //-------------------------------------------------------


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("database error", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        dataref.addListenerForSingleValueEvent(eventListener);


        this.finish();



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

                                    FirebaseUser loginuser;
                                    loginuser =authResult.getUser();
                                    getvaluesfromoutlook(loginuser,authResult);

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
                                    FirebaseUser loginuser;
                                    loginuser =authResult.getUser();

                                    //--------------------------------------------------------

                                    getvaluesfromoutlook(loginuser,authResult);
                                    //------------------------------------------------------
                                   // authResult.getAdditionalUserInfo().getProfile();

                                    Toast.makeText(getApplicationContext(),"Login success!!"+ UserInfo.fullname+UserInfo.email,Toast.LENGTH_LONG).show();

                                    Log.d(TAG, "user LOGINED  !!");



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



}}
