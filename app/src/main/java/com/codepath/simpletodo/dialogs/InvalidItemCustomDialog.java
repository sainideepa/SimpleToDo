package com.codepath.simpletodo.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.codepath.simpletodo.R;

/**
 * Created by deepasaini on 1/13/16.
 */
public class InvalidItemCustomDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savenInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.empty_item_message))
                .setPositiveButton(getString(R.string.invalid_item_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();

    }
}
