package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    Context context;
    ArrayList<member> mytodos;

    public Adapter(Context c, ArrayList<member> p){
        context = c;
        mytodos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      //  Toast.makeText(context,"2000",Toast.LENGTH_LONG).show();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does2, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        //if (mytodos.get(i).getQuesName())
        String temp = mytodos.get(i).getQuesName();
       // Toast.makeText(context,"1000",Toast.LENGTH_LONG).show();

        viewHolder.question.setText(mytodos.get(i).getQuesName());
        viewHolder.option1.setText(mytodos.get(i).getOp0Name());
        viewHolder.option2.setText(mytodos.get(i).getOp1Name());
        viewHolder.option3.setText(mytodos.get(i).getOp2Name());
        viewHolder.option4.setText(mytodos.get(i).getOp3Name());
        viewHolder.option5.setText(mytodos.get(i).getOp4Name());
        //viewHolder.keydoes.setText(mytodos.get(i).getKeydoes());

//        final String getquestion = mytodos.get(i).getQuesName();
//        final String getoption1 = mytodos.get(i).getOp1Name();
//        final String getoption2 = mytodos.get(i).getOp2Name();
//        final String getoption3 = mytodos.get(i).getOp3Name();
//        final String getoption4 = mytodos.get(i).getOp4Name();
//        final String getoption5 = mytodos.get(i).getOp5Name();

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(context, EditTaskAct.class);
//                a.putExtra("titledoes",getTitleDoes);
//                a.putExtra("keydoes",getKeyDoes);
//                context.startActivity(a);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView question,option1,option2,option3,option4,option5;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            option1 = (TextView) itemView.findViewById(R.id.option1);
            option2 = (TextView) itemView.findViewById(R.id.option2);
            option3 = (TextView) itemView.findViewById(R.id.option3);
            option4 = (TextView) itemView.findViewById(R.id.option4);
            option5 = (TextView) itemView.findViewById(R.id.option5);
        }
    }
}
