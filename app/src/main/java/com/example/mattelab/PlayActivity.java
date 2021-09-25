package com.example.mattelab;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

public class PlayActivity extends AppCompatActivity implements ExitDialog.DialogClickListener, CompletedDialog.DialogClickListener {
    int rounds = 5;
    Stack<String[]> operations = new Stack<>();

    String result; // Current operation's result
    String answer = ""; // Answer input by user

    int rights = 0;
    int wrongs = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_play);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        // Adds listener for each of the number buttons
        for (int i = 0; i<=9; i++){
            int id = getResources().getIdentifier("btn_play_" + i, "id", getPackageName());
            Button btn = (Button) findViewById(id);
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToAnswer(finalI);
                }
            });
        }

        // Adds listener to ok button -> check if answer is right and go to next/results
        Button btnOk = (Button) findViewById(R.id.btn_play_check);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

        // Adds listener to backspace button -> erases one digit from answer
        Button btnBackspace = (Button) findViewById(R.id.btn_play_erase);
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromAnswer();
            }
        });

        setRounds();
        loadOperations();
        nextOperation();
        setScore();
    }

    public void setRounds() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        try {
            String selectedRounds = preferences.getString("ROUNDS", "") ;
            rounds = Integer.parseInt(selectedRounds);
        } catch (Exception e) {
            // Do nothing. Standard is set to
            if (preferences.getString("ROUNDS", "") == null){
                preferences.edit().putString("ROUNDS", "5").apply();
            }
        }
    }

    public void loadOperations() {
        String[] inOps = getResources().getStringArray(R.array.arr_play_ops);
        for (String inOp : inOps) {
            String[] op = inOp.split("=");
            operations.add(op);
        }
    }

    public void nextOperation(){
        Collections.shuffle(operations);

        String[] op = operations.pop();

        TextView txt = (TextView) findViewById(R.id.txt_play_question);
        txt.setText(op[0]);

        result = op[1];
    }

    // Adds selected digit to answer string
    public void addToAnswer(int nr){
        answer += nr;
        TextView txt = (TextView) findViewById(R.id.txt_play_answer);
        txt.setText(answer);
        txt.setAlpha((float) 1);
    }

    // Removes last digit from answer string
    public void removeFromAnswer(){
        if (answer.length() > 0) {
            int max = answer.length() - 1;
            answer = answer.substring(0,max);
            TextView txt = (TextView) findViewById(R.id.txt_play_answer);
            txt.setText(answer);
        }
    }

    // Checks if answer is correct
    public void checkAnswer(){
        if (answer.equals(result)) {
            rights++;
        } else {
            wrongs++;
        }
        setScore();

        if (rounds > 1) {
            answer = "";
            TextView txt = (TextView) findViewById(R.id.txt_play_answer);
            txt.setText(R.string.str_play_answer);
            txt.setAlpha((float) 0.5);
            rounds--;
            nextOperation();
        } else {
            DialogFragment dialog = new CompletedDialog(rights, wrongs, this);
            dialog.show(getSupportFragmentManager(), "Completed");

        }
    }

    public void setScore(){
        TextView r = (TextView) findViewById(R.id.txt_play_rights);
        r.setText(String.valueOf(rights));

        TextView w = (TextView) findViewById(R.id.txt_play_wrongs);
        w.setText(String.valueOf(wrongs));
    }

    @Override
    public void onBackPressed() {
        DialogFragment dialog = new ExitDialog();
        dialog.show(getSupportFragmentManager(), "Close");
    }

    @Override
    public void onYesClick() {
        finish();
    }

    @Override
    public void onNoClick() {
    }

    @Override
    public void onExitClick() {
        finish();
    }
}
