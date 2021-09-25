package com.s344036.mattelab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ErrorDialog extends DialogFragment {
    private DialogClickListener callback;

    // Number of rights and wrongs for display on message
    private final int right;
    private final int wrong;

    // Custom constructor to require rights/wrongs information
    public ErrorDialog(int right, int wrong){
        this.right = right;
        this.wrong = wrong;
    }

    public interface DialogClickListener{
        void onYesClick();
        void onNoClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (DialogClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Methods have not been implemented.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String msg = str(R.string.str_play_right) + " " + str(R.string.str_dialog_answers_literally) + ": " + right + "\n" +
                str(R.string.str_play_wrong) + " " + str(R.string.str_dialog_answers_literally) + ": " + wrong + "\n\n" +
                str(R.string.str_dialog_errorquest);

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.str_dialog_error).setPositiveButton(R.string.str_dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onYesClick();
            }
        }).setNegativeButton(R.string.str_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onNoClick();
            }
        }).setMessage(msg).create();
    }

    private String str(int id) {
        return getResources().getString(id);
    }

}
