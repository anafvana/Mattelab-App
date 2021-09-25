package com.s344036.mattelab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

public class PlayActivity extends AppCompatActivity implements ExitDialog.DialogClickListener, CompletedDialog.DialogClickListener, ErrorDialog.DialogClickListener {
    int rounds = 5; // Number of rounds
    Stack<String[]> operations = new Stack<>(); // Available operations

    String result; // Current operation's result
    String answer = ""; // Answer input by user

    int rights = 0; // Number of correct answers given
    int wrongs = 0; // Number of incorrect answer giver

    /*------- OVERRIDES -------*/

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

        // Loads external data
        setRounds();
        loadOperations();
        nextOperation();
        setScore();
    }

    // Saves game data if instance is interrupted
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView tv = (TextView) findViewById(R.id.txt_play_question);
        String curQuest = tv.getText().toString();

        outState.putString("INANSWER", answer);
        outState.putString("OPANSWER", result);
        outState.putString("OPQUEST", curQuest);
        outState.putSerializable("OPERATIONS", operations);
        outState.putInt("RIGHTS", rights);
        outState.putInt("WRONGS", wrongs);
        outState.putInt("ROUNDSLEFT", rounds);
    }

    // Restores current game data if instance is interrupted (ex: screen orientation changes)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Reloads current operation data
        answer = savedInstanceState.getString("INANSWER");
        setAnswer();
        result = savedInstanceState.getString("OPANSWER");
        String question = savedInstanceState.getString("OPQUEST");
        TextView txt = (TextView) findViewById(R.id.txt_play_question);
        txt.setText(question);

        //Reloads game data
        operations = (Stack<String[]>) savedInstanceState.getSerializable("OPERATIONS");
        rounds = savedInstanceState.getInt("ROUNDSLEFT");

        //Reloads score
        wrongs = savedInstanceState.getInt("WRONGS");
        rights = savedInstanceState.getInt("RIGHTS");
        setScore();
    }

    // Prevents exit (and loss of data) on back button press
    @Override
    public void onBackPressed() {
        DialogFragment dialog = new ExitDialog();
        dialog.show(getSupportFragmentManager(), "Close");
    }

    /*------- AUXILIARY METHODS -------*/

    // Set number of rounds to be played based on preferences
    private void setRounds() {
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

    // Loads operations from resources into array
    private void loadOperations() {
        String[] inOps = getResources().getStringArray(R.array.arr_play_ops);
        for (String inOp : inOps) {
            String[] op = inOp.split("=");
            operations.add(op);
        }
    }

    // Displays next operation (or an alert, if out of operations)
    private void nextOperation(){
        try {
            Collections.shuffle(operations);
            String[] op = operations.pop();

            TextView txt = (TextView) findViewById(R.id.txt_play_question);
            txt.setText(op[0]);

            result = op[1];
        } catch (EmptyStackException e){
            DialogFragment noQuestDialog = new ErrorDialog(rights, wrongs);
            noQuestDialog.show(getSupportFragmentManager(), "NoQuestions");
        }
    }

    // Adds selected digit to answer string
    private void addToAnswer(int nr){
        answer += nr;
        setAnswer();
    }

    // Removes last digit from answer string
    private void removeFromAnswer(){
        if (answer.length() > 0) {
            int max = answer.length() - 1;
            answer = answer.substring(0,max);
            setAnswer();
        }
    }

    private void setAnswer(){
        TextView txt = (TextView) findViewById(R.id.txt_play_answer);
        txt.setText(answer);
        txt.setAlpha((float) 1);
    }

    // Checks if answer is correct
    private void checkAnswer(){
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
            updateStats();
            DialogFragment dialog = new CompletedDialog(rights, wrongs, this);
            dialog.show(getSupportFragmentManager(), "Completed");
        }
    }

    // Puts current score values on screen
    private void setScore(){
        TextView r = (TextView) findViewById(R.id.txt_play_rights);
        r.setText(String.valueOf(rights));

        TextView w = (TextView) findViewById(R.id.txt_play_wrongs);
        w.setText(String.valueOf(wrongs));
    }

    // Updates statistics in persistent memory
    private void updateStats(){
        int gamesPlayed = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("PLAYED", 0 );
        int gamesWithOver50Percent = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("WON", 0);
        int gamesWithUnder50Percent = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("LOST", 0);
        int rightOperations = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("RIGHTOP", 0);
        int wrongOperations = getSharedPreferences("STATISTICS", MODE_PRIVATE).getInt("WRONGOP",0);


        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("PLAYED", ++gamesPlayed ).apply();

        if (rights > wrongs) {
            getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("WON", ++gamesWithOver50Percent).apply();
        } else {
            getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("LOST", ++gamesWithUnder50Percent).apply();
        }

        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("RIGHTOP", rightOperations+rights).apply();
        getSharedPreferences("STATISTICS", MODE_PRIVATE).edit().putInt("WRONGOP",wrongOperations+wrongs).apply();
    }

    /*------- DIALOG METHODS -------*/
    @Override
    public void onQuitClick() {
        finish();
    }

    @Override
    public void onCancelClick() {
    }

    @Override
    public void onExitClick() {
        finish();
    }

    @Override
    public void onYesClick() {
        updateStats();
        finish();
    }

    @Override
    public void onNoClick() {
        finish();
    }
}
