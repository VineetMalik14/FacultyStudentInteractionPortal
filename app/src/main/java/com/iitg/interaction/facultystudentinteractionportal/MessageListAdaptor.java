package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(getItem(position)!=null) {


            final String sender = getItem(position).sender;
            final String receiver = getItem(position).receiver;
            final String subject = getItem(position).subject;
            final String body = getItem(position).body;
            final String date = getItem(position).date;
            String msgdirection;

            //Messages msg = new Messages(sender,receiver,subject,body);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            TextView sendertv = convertView.findViewById(R.id.tv_sender);
            TextView receivertv = convertView.findViewById(R.id.tv_receiver);
            TextView subjecttv = convertView.findViewById(R.id.tv_subject);
            TextView datetimetv = convertView.findViewById(R.id.tv_datetime);
            TextView bodytv = convertView.findViewById(R.id.tv_body);
            final TextView msgdirectiontv = convertView.findViewById(R.id.tv_msgdirection);

            sendertv.setText(sender);
            receivertv.setText(receiver);
            subjecttv.setText(subject);
            datetimetv.setText(date);
            bodytv.setText(body);
            if (UserInfo.username.equals(sender)) {
                msgdirectiontv.setText("Sent");
                msgdirectiontv.setTextColor(Color.BLUE);

            } else {
                msgdirectiontv.setText("Received");
                msgdirectiontv.setTextColor(Color.GREEN);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), messageboxActivity.class);
                    intent.putExtra("subject", subject);
                    intent.putExtra("msgdirection", msgdirectiontv.getText().toString());
                    intent.putExtra("body", body);
                    intent.putExtra("sender", sender);
                    intent.putExtra("receiver", receiver);
                    intent.putExtra("datetime", date);
                    intent.putExtra("id", position);
                    Log.d("debug","clicked on position "+position);

                    mContext.startActivity(intent);

                }
            });


        }
        return convertView;

    }



}
