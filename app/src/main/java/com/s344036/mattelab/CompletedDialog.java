package com.s344036.mattelab;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CompletedDialog extends DialogFragment {
    private DialogClickListener callback;
    private int right;
    private int wrong;
    private Activity activity;

    public interface DialogClickListener{
        public void onExitClick();
    }

    public CompletedDialog(int right, int wrong, Activity activity){
        this.right = right;
        this.wrong = wrong;
        this.activity = activity;
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
        String title = (right > wrong) ? str(R.string.str_dialog_goodresults) : str(R.string.str_dialog_badresults);
        String results = str(R.string.str_play_right) + " " + str(R.string.str_dialog_answers_literally) + ": " + right + "\n" +
                str(R.string.str_play_wrong) + " " + str(R.string.str_dialog_answers_literally) + ": " + wrong;

        return new AlertDialog.Builder(getActivity()).setTitle(title).setPositiveButton(R.string.str_dialog_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onExitClick();
            }
        }).setMessage(results).create();
    }

    public String str(int id) {
        return getResources().getString(id);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        activity.finish();
    }

}
