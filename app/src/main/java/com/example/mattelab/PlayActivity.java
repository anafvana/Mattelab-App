package com.example.mattelab;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {
    String answer = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_play);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Log.d("TAG", "Test");
        // Adds listener to back button on top bar (finish activity)
        ImageView imageView = (ImageView) findViewById(R.id.img_play_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
                //TODO Include method that checks answer and "exports" it
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
    }

    // Adds selected digit to answer string
    public void addToAnswer(int nr){
        answer += nr;
        TextView txt = (TextView) findViewById(R.id.txt_play_answer);
        txt.setText(answer);
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
    public boolean checkAnswer(){
        try {
            int inAnswer = Integer.parseInt(answer);
            return false;
        } catch (NumberFormatException e){
            //Do something
            return false;
        }
    }
}
