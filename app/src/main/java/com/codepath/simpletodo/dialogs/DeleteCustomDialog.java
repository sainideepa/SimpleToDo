package com.codepath.simpletodo.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.activities.MainActivity;

/**
 * Created by deepasaini on 1/14/16.
 */
public class DeleteCustomDialog extends DialogFragment {

    int pos;

    public DeleteCustomDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments().getInt("pos");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.delete_message))
                .setPositiveButton(getString(R.string.delete_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).deleteItem(pos);
                    }
                })
                .setNegativeButton(getString(R.string.delete_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();;
                    }
                });
        return builder.create();

    }
}
