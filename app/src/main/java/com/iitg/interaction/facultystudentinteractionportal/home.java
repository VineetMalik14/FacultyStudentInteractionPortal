package com.iitg.interaction.facultystudentinteractionportal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {

    DatabaseReference databaseReference;
    // Navigation Bar

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    NotificationManager notificationManager;
    NotificationChannel channel;
    NotificationCompat.Builder builder;



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            notification.child("users").child(UserInfo.username).child("messages").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (getApplicationContext() != null) {
                        // now we have to change show notifications whenever the number of messages is changed
                        int i = 1;

                        for (DataSnapshot message : dataSnapshot.getChildren()) {

                            if (message.child("read").getValue().toString().equals("false")) {
                                i++;
                                Toast.makeText(getApplicationContext(),"You got a new message",Toast.LENGTH_LONG).show();
                                // Create a notification
                                notificationManager = (NotificationManager)(getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE));

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationChannel channel = new NotificationChannel(
                                            "CHANNEL_ID",
                                            "My App",
                                            NotificationManager.IMPORTANCE_DEFAULT
                                    );
                                    notificationManager.createNotificationChannel(channel);
                                }
//

                                builder = new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID");
                                builder.setContentTitle(message.child("sender").getValue().toString())
                                        .setContentText(message.child("subject").getValue().toString())
                                        .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message.child("body").getValue(String.class)))
                                        .setSmallIcon(R.drawable.ic_menu_send)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setAutoCancel(true);



                                notificationManager.notify(i, builder.build());



                            }
                        }
                    }
                };

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    };

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    DatabaseReference notification = FirebaseDatabase.getInstance().getReference();








    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (UserInfo.username != null){

//        }
        handler.postDelayed(runnable,0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




        // Navigation drawer things*********************************************


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.bringToFront();
        View header = navigationView.getHeaderView(0);
        TextView name  = header.findViewById(R.id.tv_name);
        name.setText(UserInfo.fullname);

        ImageView profilePic  = header.findViewById(R.id.display_pic);
        profilePic.setImageResource(R.mipmap.ic_launcher);



        /*if (UserInfo.profilepicurl!=null){
            Picasso.with(this).load(UserInfo.profilepicurl).into(profilePic);
            Log.d("debug","photo load  dkjlfjakls "+ UserInfo.profilepicurl);
            Toast.makeText(getApplicationContext(),UserInfo.profilepicurl.toString()+"hello",Toast.LENGTH_LONG).show();
        }
        else{
            profilePic.setImageResource(R.mipmap.ic_launcher);
        }
*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_profile) {
                    Intent intent = new Intent(home.this,ProfileActivity.class);
                    startActivity(intent);

                } else if (id == R.id.courses) {
                    Intent intent = new Intent(home.this,CourseSearchTabbed.class);
                    startActivity(intent);



                } else if (id == R.id.exam_timetable) {
                    Intent intent = new Intent(home.this,ExamTimetable.class);
                    startActivity(intent);



                } else if (id == R.id.nav_FAQ) {



                }

                else if (id == R.id.nav_logout) {

                    SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putBoolean("logined",false);
                    editor.apply();
                    UserInfo.logout();
                    Intent intent = new Intent(home.this,LoginActivity.class);
                    startActivity(intent);

                    finish();

                } else if (id == R.id.nav_message) {

                    Intent intent = new Intent(home.this,MessageActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_outlook) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.microsoft.office.outlook");
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            rootView = inflater.inflate(R.layout.fragment_home, container, false);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    EventsMainPage eventsMainPage = new EventsMainPage();
                    return eventsMainPage;

                case 1:
                    ToDo toDo = new ToDo();
                    return toDo;

                case 2:
                    TimeTable timeTable= new TimeTable();
                    return timeTable;

            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}


