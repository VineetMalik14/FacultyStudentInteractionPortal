package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class disussion_course extends AppCompatActivity {

    ListView ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disussion_course);




        ll = (ListView) findViewById(R.id.lv_courses);
        TextView emp = findViewById(R.id.emptylist);
        if(!(UserInfo.courses==null)) {
            final ArrayAdapter<String> adpater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,UserInfo.courses);
            ll.setAdapter(adpater);
        }
        else
        {
            emp.setVisibility(View.VISIBLE);
        }

        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(disussion_course.this,disussion_threadActivity.class);
                intent.putExtra("course",selectedItem);
                startActivity(intent);

            }
        });



    }
}

