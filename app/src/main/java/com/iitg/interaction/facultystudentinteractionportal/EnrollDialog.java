package com.iitg.interaction.facultystudentinteractionportal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EnrollDialog extends AppCompatDialogFragment {

    private EditText keyyy;
    private EnrollDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
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

                        String key = keyyy.getText().toString();
                        listener.applyTexts(key);
                    }
                });
        keyyy = view.findViewById(R.id.test_key);

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
