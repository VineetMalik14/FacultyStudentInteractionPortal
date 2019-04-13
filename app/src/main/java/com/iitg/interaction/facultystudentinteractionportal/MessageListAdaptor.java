package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageListAdaptor extends ArrayAdapter<Messages> {
    private static final String TAG = "MessageListAdaptor";
    private Context mContext;
    private int mResource;

    public MessageListAdaptor(Context context, int resource, List<Messages> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        String sender = getItem(position).sender;
        String receiver = getItem(position).receiver;
        String subject = getItem(position).subject;
        String body = getItem(position).body;
        String date = getItem(position).date;
        String msgdirection;

        //Messages msg = new Messages(sender,receiver,subject,body);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        TextView sendertv= convertView.findViewById(R.id.tv_sender);
        TextView subjecttv = convertView.findViewById(R.id.tv_subject);
        TextView datetimetv = convertView.findViewById(R.id.tv_datetime);
        TextView bodytv = convertView.findViewById(R.id.tv_body);
        TextView msgdirectiontv = convertView.findViewById(R.id.tv_msgdirection);

        sendertv.setText(sender);
        subjecttv.setText(subject);
        datetimetv.setText(date);
        bodytv.setText(body);
        if(UserInfo.username.equals(sender))
        {
            msgdirectiontv.setText("Sent");
            msgdirectiontv.setTextColor(Color.BLUE);

        }
        else {
            msgdirectiontv.setText("Received");
            msgdirectiontv.setTextColor(Color.GREEN);
        }


        return convertView;

    }
}
