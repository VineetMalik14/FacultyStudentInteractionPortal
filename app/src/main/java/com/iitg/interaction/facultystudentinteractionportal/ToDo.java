package com.iitg.interaction.facultystudentinteractionportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO Remember to check for getActivity() and getView() while merging with the master integrating fragments
public class ToDo extends Fragment {

    String username;
    String usertype;
    ArrayList<String> todos;
    ArrayList<String> ids;
    TodoAdapter adapter;
    private DatabaseReference databaseReference;
    ListView listView;
    View dialogView;
    View dialogView2;
    private DatabaseReference databaseReference3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_to_do, container, false);
        setHasOptionsMenu(true);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_to_do);

        username = UserInfo.username;
        usertype = UserInfo.usertype;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("todo");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getView() != null && getActivity() != null){
                    todos = new ArrayList<String>();
                    ids = new ArrayList<String>();
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        ids.add(messageSnapshot.getKey());
                        todos.add(messageSnapshot.getValue().toString());

                        //  Log.v("Title", event.getTitle());
                    }


                    Collections.reverse(todos);
                    Collections.reverse(ids);


                    adapter = new TodoAdapter(getActivity(), todos);
                    listView = (ListView) getView().findViewById(R.id.todo);
                    listView.setAdapter(adapter);

                    registerForContextMenu(listView);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        FloatingActionButton newthread =  getView().findViewById(R.id.addtodo);

        newthread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.add_todo_dialogview, null);
                dialogBuilder.setView(dialogView);

                final AlertDialog b = dialogBuilder.create();
                b.setTitle("Add New Todo");
                b.show();


                Button addthread =  dialogView.findViewById(R.id.buttonAddTodo);

                addthread.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText todo = dialogView.findViewById(R.id.Todo);
                        if (todo.getText().toString() == ""){
                            Toast.makeText(getActivity(), "Please give a valid todo to add", Toast.LENGTH_SHORT).show();
                        }else{
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("todo");
                            String key = data.push().getKey();
                            data.child(key).setValue(todo.getText().toString());
                            b.dismiss();
                        }

                    }
                });





            }
        });





    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.todo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
            String[] menuItems = {"Delete Todo", "Update Todo"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);

            }
        }


    }





    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String key = ids.get(info.position);
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("users").child(UserInfo.username).child("todo").child(key);

        if (menuItemIndex == 0){ // This is to delete the

            databaseReference3.removeValue();
            // succesfull
            return true;
        }
        if (menuItemIndex == 1){      // this is to update thread

            // this is to open a new dialogue box
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            dialogView2 = inflater.inflate(R.layout.update_todo_dialog_view, null);
            dialogBuilder.setView(dialogView2);

            final AlertDialog b = dialogBuilder.create();
            b.setTitle("Update Todo");
            b.show();
            Button button = dialogView2.findViewById(R.id.buttonUpdateTodo);
            EditText todo2 = dialogView2.findViewById(R.id.Todo);
            todo2.setText(todos.get(info.position));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText todo = dialogView2.findViewById(R.id.Todo);


                    if (todo.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(), "Enter a valid todo value to enter", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseReference3.setValue(todo.getText().toString());
                        b.dismiss();
                    }
                }
            });




            return true;
        }

        return true;


    }





    public class TodoAdapter extends ArrayAdapter<String> {
        public TodoAdapter(Context context, List<String> threads) {
            super(context, 0, threads);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            String todo = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_todo, parent, false);
            }
            // Lookup view for data population
            TextView todo_text = (TextView) convertView.findViewById(R.id.todo_text);
            // Populate the data into the template view using the data object
            todo_text.setText(todo);

            // Return the completed view to render on screen
            return convertView;
        }
    }




}