package com.iitg.interaction.facultystudentinteractionportal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EnrollCourseStudent extends AppCompatDialogFragment {

    private EditText keyyy;
    private EnrollDialogListener listener;
    View view;
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.activity_course_student_list, container, false);
//        return rootView;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_enroll_course_student, null);

        builder.setView(view)
                .setTitle("")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        keyyy = view.findViewById(R.id.test_key);
                        String key = keyyy.getText().toString();
                        listener.applyTexts(key);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener =(EnrollDialogListener) context;
    }

    public interface EnrollDialogListener{
        void applyTexts(String key);
    }
}
