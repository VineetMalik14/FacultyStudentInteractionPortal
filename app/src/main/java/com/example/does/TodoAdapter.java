package com.example.does;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> implements View.OnCreateContextMenuListener {

    Context context;
    ArrayList<Mytodo> mytodos;

    public TodoAdapter(Context c, ArrayList<Mytodo> p){
        context = c;
        mytodos = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.titledoes.setText(mytodos.get(i).getTitledoes());
        //viewHolder.keydoes.setText(mytodos.get(i).getKeydoes());

        final String getTitleDoes = mytodos.get(i).getTitledoes();
        final String getKeyDoes = mytodos.get(i).getKeydoes();

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        MenuItem Edit =  menu.add(Menu.NONE, 1, 1, "Edit");//groupId, itemId, order, title
        MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titledoes,keydoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
        }
    }
}

