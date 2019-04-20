//package com.example.myapplication;
package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class pieChart extends AppCompatActivity {
    List<List<Integer>> data = new ArrayList<>();
    List<List<String>> names = new ArrayList<>();
    List<String> quesname = new ArrayList<>();
    ArrayList<member> list = new ArrayList<>();
    List<Integer> total = new ArrayList<>();
    String concat = "";
    Integer index = 0;
    DatabaseReference ref;
    Button next,prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        TextView texter;
//        texter = findViewById(R.id.texter);
        ref = FirebaseDatabase.getInstance().getReference().child("Feedback").child(CourseMainPageStudent.courseID).child("FeedbackCourse");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    // Toast.makeText(getApplicationContext(), "getting or not", Toast.LENGTH_LONG).show();
                    member p = dataSnapshot1.getValue(member.class);
                    list.add(p);
                    Toast.makeText(getApplicationContext(), Integer.toString(list.size()), Toast.LENGTH_SHORT).show();

                }
//                concat = Integer.toString(list.size());
//                texter.setText(concat);
                for (int i = 0;i<list.size();i++)
                {
                    quesname.add("");
                    data.add(new ArrayList<Integer>());
                    names.add(new ArrayList<String>());
                    total.add(0);
                }
                for (int i = 0;i<list.size();i++)
                {
                    quesname.set(i,list.get(i).getQuesName());

//                    concat = concat+" "+quesname.get(i);
//                    texter.setText(quesname.get(i));
                    for (int j = 0;j<5;j++)
                    {
                        String name="";
                        int datas = 0;
                        switch (j){
                            case 0: name= list.get(i).getOp0Name();
                                datas = list.get(i).getOp0value();
                                break;
                            case 1: name = list.get(i).getOp1Name();
                                datas = list.get(i).getOp1value();
                                break;
                            case 2: name = list.get(i).getOp2Name();
                                datas = list.get(i).getOp2value();
                                break;
                            case 3: name = list.get(i).getOp3Name();
                                datas = list.get(i).getOp3value();
                                break;
                            case 4: name = list.get(i).getOp4Name();
                                datas = list.get(i).getOp4value();
                                break;
                        }
                        if (!(name.isEmpty()))
                        {
                            names.get(i).add(name);
                            data.get(i).add(datas);
                            Integer trash = total.get(i) + datas;
                            total.set(i,trash);
                        }
                    }
                    //setupPieChart();
                }
                if (index>=0 && index <list.size()) {
                    TextView caption = findViewById(R.id.caption);
                    caption.setText(quesname.get(index));
                    TextView Total = findViewById(R.id.total);
                    String blah = "Total number of responses are " + Integer.toString(total.get(index));
                    Total.setText(blah);
                    setupPieChart();
                }
                if (index >= list.size()-1 )
                {
                    next.setEnabled(false);

                }
//                if (index <= 0)
//                {
//                    prev.setEnabled(false);
//
//                }
                next = findViewById(R.id.next);
                prev = findViewById(R.id.prev);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "On Click", Toast.LENGTH_SHORT).show();

                        if (index >= list.size()-1 )
                        {
                            next.setEnabled(false);

                        }else
                        {
                            index++;
                            if (index >=0 && index < list.size()) {
                                //Toast.makeText(getApplicationContext(), "setup pie chart", Toast.LENGTH_SHORT).show();
                                TextView caption = findViewById(R.id.caption);
                                caption.setText(quesname.get(index));
                                TextView Total = findViewById(R.id.total);
                                String blah = "Total number of responses are " + Integer.toString(total.get(index));
                                Total.setText(blah);
                                setupPieChart();
                            }
                        }
                    }
                });
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "On Click", Toast.LENGTH_SHORT).show();

                        if (index <= 0)
                        {
                            prev.setEnabled(false);

                        }else
                        {
                            index--;
                            if (index>=0 && index<list.size()) {
                                //Toast.makeText(getApplicationContext(), "setup pie chart", Toast.LENGTH_SHORT).show();
                                TextView caption = findViewById(R.id.caption);
                                caption.setText(quesname.get(index));
                                TextView Total = findViewById(R.id.total);
                                String blah = "Total number of responses are " + Integer.toString(total.get(index));
                                Total.setText(blah);
                                setupPieChart();
                            }
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0;i<data.get(index).size();i++)
        {
            pieEntries.add(new PieEntry(data.get(index).get(i),names.get(index).get(i)));
            PieDataSet dataSet = new PieDataSet(pieEntries,"");
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);
            PieData data = new PieData(dataSet);

            // Toast.makeText(getApplicationContext(), "Make chart", Toast.LENGTH_SHORT).show();

            PieChart chart = findViewById(R.id.pie);
            chart.setData(data);
            chart.animateY(1000);
            chart.setUsePercentValues(true);
            data.setValueTextSize(13f);
            data.setValueFormatter(new PercentFormatter());
            data.setValueFormatter(new PercentFormatter(chart));
            chart.setHighlightPerTapEnabled(true);
            chart.invalidate();
        }
    }
}

