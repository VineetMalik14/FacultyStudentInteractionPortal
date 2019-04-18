package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PollListAdaptor extends ArrayAdapter<Options> {

    private static final String TAG = "PollListAdaptor";
    private Context mContext;
    private int mResource;

    public PollListAdaptor(Context context, int resource, List<Options> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        String optiontext = getItem(position).optiontext;
        float votes = getItem(position).votes;
        float totalvotes = getItem(position).totalvotes;
        float votepercent = (votes*100)/totalvotes;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView optiontv  = convertView.findViewById(R.id.tv_option) ;
        ProgressBar progressbarpv = convertView.findViewById(R.id.pb_option);
        TextView percentvotetv = convertView.findViewById(R.id.tv_percentvote);
        RelativeLayout rl = convertView.findViewById(R.id.rl_polloption);
        optiontv.setText(optiontext);
        progressbarpv.setProgress((int)votepercent);
        percentvotetv.setText(String.format("%.1f", votepercent)+"%");

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), messageboxActivity.class);
//                intent.putExtra("subject", subject);
//                intent.putExtra("msgdirection", msgdirectiontv.getText().toString());
//                intent.putExtra("body", body);
//                intent.putExtra("sender", sender);
//                intent.putExtra("receiver", receiver);
//                intent.putExtra("datetime", date);
//                intent.putExtra("id", position);
//                Log.d("debug","clicked on position "+position);
//
//                mContext.startActivity(intent);
//
//            }
//        });
//
//
//    }
        return convertView;

    }
}
